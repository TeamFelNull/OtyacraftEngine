package dev.felnull.otyacraftengine.client.renderer.texture;

import dev.felnull.otyacraftengine.client.util.OETextureUtils;
import dev.felnull.otyacraftengine.util.FlagThread;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NativeTextureManager {
    private static final Minecraft mc = Minecraft.getInstance();
    private static final NativeTextureManager INSTANCE = new NativeTextureManager();
    private final Map<UUID, NativeTextureLoadResult> NATIVE_TEXTURE_LOADS = new HashMap<>();
    private final Map<UUID, NativeTextureLoader> NATIVE_TEXTURE_LOADERS = new HashMap<>();

    public static NativeTextureManager getInstance() {
        return INSTANCE;
    }

    @NotNull
    public NativeTextureLoadResult getAndLoadTextureAsync(@NotNull UUID uuid, @NotNull InputStream stream) {
        NativeTextureLoadResult r;
        synchronized (NATIVE_TEXTURE_LOADS) {
            r = NATIVE_TEXTURE_LOADS.get(uuid);
        }
        if (r == null) {
            r = new NativeTextureLoadResult();
            synchronized (NATIVE_TEXTURE_LOADS) {
                NATIVE_TEXTURE_LOADS.put(uuid, r);
            }
            NativeTextureLoader loader = new NativeTextureLoader(uuid, stream);
            synchronized (NATIVE_TEXTURE_LOADERS) {
                NATIVE_TEXTURE_LOADERS.put(uuid, loader);
            }
            loader.start();
        }
        return r;
    }

    public NativeTextureLoadResult getAndLoadTexture(@NotNull UUID uuid, @NotNull InputStream stream) {
        NativeTextureLoadResult r;
        synchronized (NATIVE_TEXTURE_LOADS) {
            r = NATIVE_TEXTURE_LOADS.get(uuid);
        }
        if (r == null) {
            r = loadNativeTexture(stream);
            synchronized (NATIVE_TEXTURE_LOADS) {
                NATIVE_TEXTURE_LOADS.put(uuid, r);
            }
        }
        return r;
    }

    @Nullable
    public NativeTextureLoadResult getTexture(@NotNull UUID uuid) {
        synchronized (NATIVE_TEXTURE_LOADS) {
            return NATIVE_TEXTURE_LOADS.get(uuid);
        }
    }

    public void freeNativeTexture(@NotNull UUID id) {
        synchronized (NATIVE_TEXTURE_LOADERS) {
            var l = NATIVE_TEXTURE_LOADERS.get(id);
            if (l != null) {
                l.stopped();
                NATIVE_TEXTURE_LOADERS.remove(id);
                return;
            }
        }
        synchronized (NATIVE_TEXTURE_LOADS) {
            var r = NATIVE_TEXTURE_LOADS.get(id);
            if (r != null) {
                if (r.isSuccess())
                    OETextureUtils.freeTexture(r.location());
                NATIVE_TEXTURE_LOADS.remove(id);
            }
        }
    }

    private static NativeTextureLoadResult loadNativeTexture(InputStream stream) {
        ResourceLocation[] loc = new ResourceLocation[1];
        try (BufferedInputStream bufstream = new BufferedInputStream(stream)) {
            var tx = OETextureUtils.createNativeTexture(bufstream);
            mc.submit(() -> {
                loc[0] = mc.getTextureManager().register("native_texture", tx);
            }).get();
        } catch (Exception ex) {
            return new NativeTextureLoadResult(null, ex);
        }
        return new NativeTextureLoadResult(loc[0], null);
    }

    private class NativeTextureLoader extends FlagThread {
        private final UUID uuid;
        private final InputStream stream;

        private NativeTextureLoader(UUID uuid, InputStream stream) {
            this.uuid = uuid;
            this.stream = stream;
        }

        @Override
        public void run() {
            if (isStopped()) {
                finish();
                return;
            }
            var ret = loadNativeTexture(stream);
            if (isStopped()) {
                if (ret.location() != null)
                    OETextureUtils.freeTexture(ret.location());
                finish();
                return;
            }
            synchronized (NATIVE_TEXTURE_LOADS) {
                NATIVE_TEXTURE_LOADS.put(uuid, ret);
            }
            finish();
        }

        private void finish() {
            synchronized (NATIVE_TEXTURE_LOADERS) {
                NATIVE_TEXTURE_LOADERS.remove(uuid);
            }
        }
    }
}
