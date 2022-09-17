package dev.felnull.otyacraftengine.client.renderer.texture;

import dev.felnull.otyacraftengine.client.renderer.texture.impl.URLTextureManagerNewImpl;

public interface URLTextureManager {
    static URLTextureManager getInstance() {
        return URLTextureManagerNewImpl.INSTANCE;
    }

    TextureLoadResult getAndAsyncLoad(String url, boolean cached);

    void init();

    void save();

    void release();

    void tick();
}
