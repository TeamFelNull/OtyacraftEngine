package dev.felnull.otyacraftengine.client.renderer.texture.impl;

/*
public class URLTextureManagerOldImpl implements URLTextureManager {
    public static final URLTextureManagerOldImpl INSTANCE = new URLTextureManagerOldImpl();
    private static final Minecraft mc = Minecraft.getInstance();
    private static final Gson GSON = new Gson();
    private final Map<String, UUID> FILE_CACHE_TEXTURE_IDS = new HashMap<>();
    private final Map<String, URLTextureLoadResult> LOAD_URL_TEXTURE = new HashMap<>();
    private final List<URLTextureLoader> URL_TEXTURE_LOADERS = new ArrayList<>();
    private final List<LoadURLEntry> WAIT_LOAD_URLS = new ArrayList<>();
    private final List<LoadURLEntry> REMOVE_WAIT_URLS = new ArrayList<>();
    private Function<String, String> HASH_CACHE = createHashMemoize();
    private boolean dirtyFileCache;
    private long lastSave = -1;
    private long lastReload = -1;
    private URLTextureSaveThread saveThread;

    @Override
    public TextureLoadResult getAndAsyncLoad(String url, boolean cached) {
        var hash = HASH_CACHE.apply(url);
        synchronized (LOAD_URL_TEXTURE) {
            var ret = LOAD_URL_TEXTURE.get(hash);
            if (ret == null) {
                ret = new URLTextureLoadResult(null, false, -1, null, null);
                ret.setProgress(new TextureLoadProgressImpl("Load waiting", LOAD_URL_TEXTURE.size(), 0));
                LOAD_URL_TEXTURE.put(hash, ret);
                synchronized (WAIT_LOAD_URLS) {
                    WAIT_LOAD_URLS.add(new LoadURLEntry(url, cached));
                }
            }
            return ret;
        }
    }

    public void init() {
        loadIndex();
        optimizationFileCache();
    }

    public void tick() {
        if (mc.level == null) return;

        synchronized (WAIT_LOAD_URLS) {
            int ct = 0;
            int lt = Math.max(OtyacraftEngine.getConfig().getClientConfig().getUrlTextureConfig().getMaxLoaderCount() - URL_TEXTURE_LOADERS.size(), 0);
            for (LoadURLEntry waitUrl : WAIT_LOAD_URLS) {
                if (ct >= lt) break;
                REMOVE_WAIT_URLS.add(waitUrl);
                ct++;
            }
            for (LoadURLEntry rwu : REMOVE_WAIT_URLS) {
                WAIT_LOAD_URLS.remove(rwu);
                URLTextureLoadResult ret;
                synchronized (LOAD_URL_TEXTURE) {
                    ret = LOAD_URL_TEXTURE.get(HASH_CACHE.apply(rwu.url));
                }
                ret.setProgress(new TextureLoadProgressImpl("Load waiting", LOAD_URL_TEXTURE.size(), LOAD_URL_TEXTURE.size()));
                var loader = new URLTextureLoader(rwu.url, rwu.cached, ret);
                loader.start();
                URL_TEXTURE_LOADERS.add(loader);
            }
            REMOVE_WAIT_URLS.clear();
        }

        if (lastSave <= -1) lastSave = System.currentTimeMillis();

        if (System.currentTimeMillis() - lastSave >= 1000 * 60 * 3) {
            lastSave = System.currentTimeMillis();
            if (dirtyFileCache && saveThread == null) {
                saveThread = new URLTextureSaveThread();
                saveThread.start();
            }
        }

        if (lastReload <= -1) lastReload = System.currentTimeMillis();

        if (System.currentTimeMillis() - lastReload >= 1000 * 60 * 20) {
            lastReload = System.currentTimeMillis();
            List<String> removes = new ArrayList<>();
            synchronized (LOAD_URL_TEXTURE) {
                for (Map.Entry<String, URLTextureLoadResult> entry : LOAD_URL_TEXTURE.entrySet()) {
                    var ret = entry.getValue();
                    if (ret.isNeedReload() && ret.isError() && ret.getLoadedTime() >= 0 && System.currentTimeMillis() - ret.getLoadedTime() >= 1000 * 60 * 60) {
                        removes.add(entry.getKey());
                    }
                }
                for (String remove : removes) {
                    LOAD_URL_TEXTURE.remove(remove);
                }
            }
        }
    }


    private void updateWaitProgress() {
        synchronized (LOAD_URL_TEXTURE) {
            for (URLTextureLoadResult value : LOAD_URL_TEXTURE.values()) {
                value.setProgress(new TextureLoadProgressImpl(value.getProgress().getStateName(), value.getProgress().getTotal(), value.getProgress().getComplete() + 1));
            }
        }
    }

    public synchronized void saveIndex() {
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

    public synchronized void loadIndex() {
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

    public void optimizationFileCache() {
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

    public void reload() {
        saveIndex();
        clear();
    }

    public synchronized void clear() {
        HASH_CACHE = createHashMemoize();
        synchronized (URL_TEXTURE_LOADERS) {
            URL_TEXTURE_LOADERS.forEach(utl -> {
                if (utl != null) utl.stopped();
            });
            URL_TEXTURE_LOADERS.clear();
        }
        synchronized (LOAD_URL_TEXTURE) {
            for (URLTextureLoadResult value : LOAD_URL_TEXTURE.values()) {
                if (value.getUUID() != null) NativeTextureManager.getInstance().freeNativeTexture(value.getUUID());
            }
            LOAD_URL_TEXTURE.clear();
        }
        synchronized (WAIT_LOAD_URLS) {
            WAIT_LOAD_URLS.clear();
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

        //if (ALLOW_URL_REGEX.stream().map(n -> Pattern.compile(n.get()).matcher(url)).noneMatch(Matcher::matches))
        //    throw new IllegalArgumentException("Not allowed URL regex");

        boolean oea = Pattern.compile(OtyacraftEngine.getConfig().getClientConfig().getUrlTextureConfig().getUrlRegex()).matcher(url).matches();
        boolean eva = OEClientEventHooks.onCheckTextureURL(url);
        boolean aflg = oea || eva;

        if (!aflg) throw new IllegalArgumentException("Not allowed URL");
    }

    private Pair<String, URLTextureLoadResult> loadUrlTexture(String url, boolean cached, Consumer<TextureLoadProgress> progress) {
        var hash = HASH_CACHE.apply(url);
        try {
            checkUrl(url);
        } catch (Exception ex) {
            return Pair.of(hash, new URLTextureLoadResult(ex, false, null, UUID.randomUUID()));
        }

        UUID fileCache;
        synchronized (FILE_CACHE_TEXTURE_IDS) {
            fileCache = FILE_CACHE_TEXTURE_IDS.get(hash);
        }

        NativeTextureManager ntm = NativeTextureManager.getInstance();
        if (fileCache != null) {
            try {
                var ret = ntm.getAndLoadTexture(fileCache, new FileInputStream(getFileCachePath(fileCache).toFile()), progress);
                return Pair.of(hash, new URLTextureLoadResult(null, false, ret, fileCache));
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
            var con = connect(url);
            stream = con.getLeft();
            length = con.getRight();
        } catch (IOException e) {
            return Pair.of(hash, new URLTextureLoadResult(e, true, null, uuid));
        }

        if (cached) {
            try {
                var fil = getFileCachePath(uuid).toFile();
                fil.getParentFile().mkdirs();


                progress.accept(new TextureLoadProgressImpl("Cache getting", (int) length, 0));
                try (InputStream inputStream = stream) {
                    FNDataUtil.fileWriteToProgress(inputStream, length, fil, progressListener -> progress.accept(new TextureLoadProgressImpl("Cache getting", (int) progressListener.getWrittenLength(), (int) progressListener.getLength())));
                }
                progress.accept(new TextureLoadProgressImpl("Cache getting", (int) length, (int) length));

                stream = new FileInputStream(getFileCachePath(uuid).toFile());
                synchronized (FILE_CACHE_TEXTURE_IDS) {
                    FILE_CACHE_TEXTURE_IDS.put(hash, uuid);
                    dirtyFileCache = true;
                }
            } catch (IOException e) {
                return Pair.of(hash, new URLTextureLoadResult(e, true, null, uuid));
            }
        }

        var ret = ntm.getAndLoadTexture(uuid, stream, progress);
        return Pair.of(hash, new URLTextureLoadResult(null, false, ret, uuid));
    }

    private Pair<InputStream, Long> connect(String url) throws IOException {
        var con = FNURLUtil.getConnection(new URL(OEClientEventHooks.onSwapTextureURL(url)));
        long max = 1024L * 1024L * 10;

        try (InputStream in = con.getInputStream(); InputStream bin = new BufferedInputStream(in); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            long size = FNDataUtil.inputToOutputLimit(bin, out, max);
            if (size <= -1)
                throw new IOException("Size Over: " + max + "byte");

            return Pair.of(new ByteArrayInputStream(out.toByteArray()), size);
        }
    }

    private record LoadURLEntry(String url, boolean cached) {
    }

    private class URLTextureLoader extends FlagThread {
        private final String url;
        private final boolean cached;
        private final URLTextureLoadResult result;

        private URLTextureLoader(String url, boolean cached, URLTextureLoadResult result) {
            this.url = url;
            this.cached = cached;
            this.result = result;
            setName(OtyacraftEngine.getModName() + "-URL texture loader thread");
        }

        @Override
        public void run() {
            if (isStopped()) {
                finished();
                return;
            }
            var ret = loadUrlTexture(url, cached, result::setProgress);
            var retHash = ret.getKey();
            var retEntry = ret.getValue();
            if (retEntry.getNativeResult() != null && retEntry.getNativeResult().getException() != null) {
                retEntry = new URLTextureLoadResult(retEntry.getException(), true, null, retEntry.getUUID());
            }

            if (isStopped()) {
                if (retEntry.getUUID() != null)
                    NativeTextureManager.getInstance().freeNativeTexture(retEntry.getUUID());
                finished();
                return;
            }

            synchronized (LOAD_URL_TEXTURE) {
                LOAD_URL_TEXTURE.put(retHash, retEntry);
            }

            finished();
        }

        private void finished() {
            URL_TEXTURE_LOADERS.remove(this);
            updateWaitProgress();
        }
    }

    private class URLTextureSaveThread extends Thread {
        private URLTextureSaveThread() {
            setName("OtyacraftEngine-URLTextureSave thread");
        }

        @Override
        public void run() {
            saveIndex();
            lastSave = System.currentTimeMillis();
            saveThread = null;
        }
    }
}
*/