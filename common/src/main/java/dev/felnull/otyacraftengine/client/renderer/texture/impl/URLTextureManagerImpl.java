package dev.felnull.otyacraftengine.client.renderer.texture.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.felnull.fnjl.util.FNDataUtil;
import dev.felnull.fnjl.util.FNStringUtil;
import dev.felnull.fnjl.util.FNURLUtil;
import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.client.event.OEClientEventHooks;
import dev.felnull.otyacraftengine.client.renderer.texture.NativeTextureManager;
import dev.felnull.otyacraftengine.client.renderer.texture.TextureLoadProgress;
import dev.felnull.otyacraftengine.client.renderer.texture.TextureLoadResult;
import dev.felnull.otyacraftengine.client.renderer.texture.URLTextureManager;
import dev.felnull.otyacraftengine.util.OEPaths;
import net.minecraft.client.Minecraft;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Pattern;

public class URLTextureManagerImpl implements URLTextureManager {
    public static final URLTextureManagerImpl INSTANCE = new URLTextureManagerImpl();
    private static final Minecraft mc = Minecraft.getInstance();
    private static final Gson GSON = new Gson();
    private final Map<String, UUID> FILE_CACHE_TEXTURE_IDS = new HashMap<>();
    private final Object FILE_CACHE_IO_LOCK = new Object();
    private final Map<String, URLTextureEntry> LOAD_URL_TEXTURE = new HashMap<>();
    private ExecutorService executorService = createExecutorService();
    private Function<String, String> HASH_CACHE = createHashMemoize();
    private boolean dirtyFileCache;
    private long lastSave = -1;
    private long lastReload = -1;

    public void init() {
        optimize();
        load();
    }

    public void save() {
        synchronized (FILE_CACHE_IO_LOCK) {
            long st = System.currentTimeMillis();

            var fol = getFileCacheFolderPath().toFile();
            if (!fol.exists() && !fol.mkdirs()) {
                OtyacraftEngine.LOGGER.error("Failed to create url textures folder");
                return;
            }

            var urlIndex = getIndexPath().toFile();

            JsonObject index = new JsonObject();

            synchronized (FILE_CACHE_TEXTURE_IDS) {
                FILE_CACHE_TEXTURE_IDS.forEach((url, id) -> {
                    var idStr = id.toString().replace("-", "");
                    var file = getFileCacheFolderPath().resolve(idStr).toFile();
                    if (file.exists()) index.addProperty(url, idStr);
                });
                dirtyFileCache = false;
            }

            urlIndex.getParentFile().mkdirs();
            try (Writer writer = new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(urlIndex)))) {
                GSON.toJson(index, writer);
            } catch (IOException e) {
                OtyacraftEngine.LOGGER.error("Failed to save url textures index", e);
            }

            if (FILE_CACHE_TEXTURE_IDS.size() > 0)
                OtyacraftEngine.LOGGER.info(String.format("Saved %s URL texture cache indexes in %s ms.", FILE_CACHE_TEXTURE_IDS.size(), System.currentTimeMillis() - st));
        }
    }

    public void load() {
        synchronized (FILE_CACHE_IO_LOCK) {
            long st = System.currentTimeMillis();

            var urlIndex = getIndexPath().toFile();
            if (!urlIndex.exists()) return;
            JsonObject index;

            try (Reader reader = new InputStreamReader(new BufferedInputStream(new FileInputStream(urlIndex)))) {
                index = GSON.fromJson(reader, JsonObject.class);
            } catch (IOException e) {
                OtyacraftEngine.LOGGER.error("Failed to load url texture cache indexes", e);
                return;
            }

            synchronized (FILE_CACHE_TEXTURE_IDS) {
                FILE_CACHE_TEXTURE_IDS.clear();
                index.entrySet().forEach(entry -> {
                    var v = entry.getValue();
                    if (v != null && v.isJsonPrimitive() && v.getAsJsonPrimitive().isString()) {
                        var uuid = FNStringUtil.getUUIDFromNoHyphenStringNonThrow(v.getAsString());
                        if (uuid != null) {
                            var file = getFileCacheFolderPath().resolve(v.getAsString()).toFile();
                            if (file.exists()) FILE_CACHE_TEXTURE_IDS.put(entry.getKey(), uuid);
                        }
                    }
                });
            }

            if (FILE_CACHE_TEXTURE_IDS.size() > 0)
                OtyacraftEngine.LOGGER.info(String.format("Loaded %s URL texture cache indexes in %s ms.", FILE_CACHE_TEXTURE_IDS.size(), System.currentTimeMillis() - st));
        }
    }

    public void clear() {
        executorService.shutdown();
        executorService = createExecutorService();

        HASH_CACHE = createHashMemoize();
    }

    private ExecutorService createExecutorService() {
        return new ThreadPoolExecutor(0, OtyacraftEngine.getConfig().getClientConfig().getUrlTextureConfig().getMaxLoaderCount(), 60L, TimeUnit.SECONDS, new SynchronousQueue<>(), new BasicThreadFactory.Builder().namingPattern("url-texture-loader-%d").daemon(true).build());
    }

    public void optimize() {
        long st = System.currentTimeMillis();

        var urlIndex = getIndexPath().toFile();
        if (!urlIndex.exists()) return;

        var fol = getFileCacheFolderPath().toFile();
        if (!fol.exists() || !fol.isDirectory()) return;

        var fils = fol.listFiles();
        if (fils == null) return;

        List<File> noExistFiles = new ArrayList<>();
        synchronized (FILE_CACHE_TEXTURE_IDS) {
            for (File fil : fils) {
                var name = fil.getName();
                boolean flg = false;
                var uuid = FNStringUtil.getUUIDFromNoHyphenStringNonThrow(name);
                if (uuid != null) flg = FILE_CACHE_TEXTURE_IDS.containsValue(uuid);

                if (!flg) noExistFiles.add(fil);
            }
        }
        int ct = 0;
        for (File neFile : noExistFiles) {
            if (neFile.delete()) ct++;
        }

        if (ct > 0)
            OtyacraftEngine.LOGGER.info(String.format("Removed %s unnecessary URL Texture Cache files in %sms.", ct, System.currentTimeMillis() - st));
    }

    public void tick() {
        if (mc.level == null) return;

        if (lastSave <= -1) lastSave = System.currentTimeMillis();

        if (System.currentTimeMillis() - lastSave >= 1000 * 60 * 3) {
            lastSave = System.currentTimeMillis();

            if (dirtyFileCache)
                CompletableFuture.runAsync(() -> {
                    save();
                    lastSave = System.currentTimeMillis();
                }, executorService);
        }

        if (lastReload <= -1) lastReload = System.currentTimeMillis();

        if (System.currentTimeMillis() - lastReload >= 1000 * 60 * 20) {
            lastReload = System.currentTimeMillis();
            List<String> removes = new ArrayList<>();
            synchronized (LOAD_URL_TEXTURE) {
                for (Map.Entry<String, URLTextureEntry> entry : LOAD_URL_TEXTURE.entrySet()) {
                    var ret = entry.getValue();
                    if (ret.result.isNeedReload() && ret.result.isError() && ret.result.getLoadedTime() >= 0 && System.currentTimeMillis() - ret.result.getLoadedTime() >= 1000 * 60 * 60) {
                        removes.add(entry.getKey());
                    }
                }
                for (String remove : removes) {
                    LOAD_URL_TEXTURE.remove(remove);
                }
            }
        }
    }

    @Override
    public TextureLoadResult getAndAsyncLoad(String url, boolean cached) {
        var hash = HASH_CACHE.apply(url);
        URLTextureEntry ret;
        synchronized (LOAD_URL_TEXTURE) {
            ret = LOAD_URL_TEXTURE.get(hash);
            if (ret == null) {
                var result = new URLTextureLoadResult(null, false, -1, null, null);
                ret = new URLTextureEntry(result, cached);
                ret.result.setProgress(new TextureLoadProgressImpl("Load waiting", 0, 0));
                LOAD_URL_TEXTURE.put(hash, ret);

                URLTextureEntry finalRet = ret;
                CompletableFuture.supplyAsync(() -> loadUrlTexture(hash, url, () -> finalRet.cached, result::setProgress), executorService).thenApplyAsync(let -> {
                    if (let.ret != null)
                        return new URLTextureLoadPipe2(null, null, null, let.ret);

                    InputStream stream = let.stream;
                    if (finalRet.cached) {
                        try {
                            writeCache(let);
                            stream = new FileInputStream(getFileCachePath(let.uuid).toFile());
                        } catch (IOException e) {
                            return new URLTextureLoadPipe2(null, null, null, Pair.of(hash, new URLTextureLoadResult(e, true, null, let.uuid)));
                        }
                    }
                    return new URLTextureLoadPipe2(let.uuid, let.progress, stream, null);
                }, executorService).thenApplyAsync(let -> {
                    if (let.ret != null)
                        return let.ret;

                    NativeTextureManager ntm = NativeTextureManager.getInstance();
                    var rat = ntm.getAndLoadTexture(let.uuid, let.stream, let.progress);

                    return Pair.of(hash, new URLTextureLoadResult(null, false, rat, let.uuid));
                }, executorService).thenApplyAsync(lt -> {
                    var retHash = lt.getKey();
                    var retEntry = lt.getValue();

                    if (retEntry.getNativeResult() != null && retEntry.getNativeResult().getException() != null)
                        retEntry = new URLTextureLoadResult(retEntry.getException(), true, null, retEntry.getUUID());

                    return Pair.of(retHash, retEntry);
                }, executorService).thenAcceptAsync(let -> {
                    synchronized (LOAD_URL_TEXTURE) {
                        LOAD_URL_TEXTURE.put(let.getKey(), new URLTextureEntry(let.getValue(), finalRet.cached));
                    }
                }, executorService);

            } else {
                if (!ret.cached && cached) {
                    ret.cached = true;
                    if (ret.result.isSuccess()) {
                        URLTextureEntry finalRet1 = ret;
                        CompletableFuture.supplyAsync(() -> {
                            try {
                                var cret = connect(url);
                                return new URLTextureLoadPipe(hash, finalRet1.result.getUUID(), cret.getRight(), null, cret.getLeft(), null);
                            } catch (Exception ex) {
                                return null;
                            }
                        }, executorService).thenAcceptAsync(let -> {
                            if (let != null) {
                                try {
                                    writeCache(let);
                                } catch (IOException ignored) {
                                }
                            }
                        }, executorService);
                    }
                }
            }
        }
        return ret.result;
    }

    private URLTextureLoadPipe loadUrlTexture(String hash, String url, BooleanSupplier cached, Consumer<TextureLoadProgress> progress) {
        try {
            checkUrl(url);
        } catch (Exception ex) {
            return new URLTextureLoadPipe(null, null, -1, null, null, Pair.of(hash, new URLTextureLoadResult(ex, false, null, UUID.randomUUID())));
        }

        UUID fileCache;
        synchronized (FILE_CACHE_TEXTURE_IDS) {
            fileCache = FILE_CACHE_TEXTURE_IDS.get(hash);
        }

        NativeTextureManager ntm = NativeTextureManager.getInstance();
        if (fileCache != null) {
            try {
                var ret = ntm.getAndLoadTexture(fileCache, new FileInputStream(getFileCachePath(fileCache).toFile()), progress);
                return new URLTextureLoadPipe(null, null, -1, null, null, Pair.of(hash, new URLTextureLoadResult(null, false, ret, fileCache)));
            } catch (FileNotFoundException e) {
                synchronized (FILE_CACHE_TEXTURE_IDS) {
                    FILE_CACHE_TEXTURE_IDS.remove(hash);
                }
            }
        }

        UUID uuid = UUID.randomUUID();
        InputStream stream;
        long length;
        try {
            var cret = connect(url);
            length = cret.getRight();
            stream = cret.getLeft();
        } catch (IOException e) {
            return new URLTextureLoadPipe(null, null, -1, null, null, Pair.of(hash, new URLTextureLoadResult(e, true, null, uuid)));
        }

        return new URLTextureLoadPipe(hash, uuid, length, progress, stream, null);
    }

    private Pair<InputStream, Long> connect(String url) throws IOException {
        var con = FNURLUtil.getConnection(new URL(OEClientEventHooks.onSwapTextureURL(url)));
        long length = con.getContentLengthLong();

        if (length <= 0) length = 1;

        long max = 1024L * 1024L * 10;

        if (length > max) throw new IOException("Size Over: " + max + "byte" + " current: " + length + "byte");

        return Pair.of(con.getInputStream(), length);
    }

    private void writeCache(URLTextureLoadPipe urlTextureLoadPipe) throws IOException {
        var fil = getFileCachePath(urlTextureLoadPipe.uuid).toFile();
        FNDataUtil.wishMkdir(fil.getParentFile());
        if (urlTextureLoadPipe.progress != null)
            urlTextureLoadPipe.progress.accept(new TextureLoadProgressImpl("Cache getting", (int) urlTextureLoadPipe.length, 0));
        try (InputStream inputStream = urlTextureLoadPipe.stream) {
            FNDataUtil.fileWriteToProgress(inputStream, urlTextureLoadPipe.length, fil, progressListener -> {
                if (urlTextureLoadPipe.progress != null)
                    urlTextureLoadPipe.progress.accept(new TextureLoadProgressImpl("Cache getting", (int) progressListener.getWrittenLength(), (int) progressListener.getLength()));
            });
        }
        if (urlTextureLoadPipe.progress != null)
            urlTextureLoadPipe.progress.accept(new TextureLoadProgressImpl("Cache getting", (int) urlTextureLoadPipe.length, (int) urlTextureLoadPipe.length));

        synchronized (FILE_CACHE_TEXTURE_IDS) {
            FILE_CACHE_TEXTURE_IDS.put(urlTextureLoadPipe.hash, urlTextureLoadPipe.uuid);
            dirtyFileCache = true;
        }
    }

    private Path getIndexPath() {
        return OEPaths.getClientOEFolderPath().resolve("url_textures_index.json");
    }

    private Path getFileCacheFolderPath() {
        return OEPaths.getClientOEFolderPath().resolve("url_texture_cache");
    }

    private Path getFileCachePath(UUID uuid) {
        return getFileCacheFolderPath().resolve(uuid.toString().replace("-", ""));
    }

    private Function<String, String> createHashMemoize() {
        return FNDataUtil.memoize(n -> {
            try {
                byte[] md5 = FNDataUtil.createMD5Hash(n.getBytes(StandardCharsets.UTF_8));
                return new String(Hex.encodeHex(md5));
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void checkUrl(String url) {
        if (url.length() > 300) throw new IllegalArgumentException("URL is too long");

        boolean oea = Pattern.compile(OtyacraftEngine.getConfig().getClientConfig().getUrlTextureConfig().getUrlRegex()).matcher(url).matches();
        boolean eva = OEClientEventHooks.onCheckTextureURL(url);
        boolean aflg = oea || eva;

        if (!aflg) throw new IllegalArgumentException("Not allowed URL");
    }

    private class URLTextureEntry {
        private URLTextureLoadResult result;
        private boolean cached;

        private URLTextureEntry(URLTextureLoadResult result, boolean cached) {
            this.result = result;
            this.cached = cached;
        }
    }

    private record URLTextureLoadPipe(String hash, UUID uuid, long length,
                                      @Nullable Consumer<TextureLoadProgress> progress,
                                      InputStream stream, Pair<String, URLTextureLoadResult> ret) {
    }

    private record URLTextureLoadPipe2(UUID uuid, Consumer<TextureLoadProgress> progress, InputStream stream,
                                       Pair<String, URLTextureLoadResult> ret) {

    }
}
