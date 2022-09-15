package dev.felnull.otyacraftengine.client.renderer.texture;

import dev.felnull.otyacraftengine.client.renderer.texture.impl.URLTextureManagerImpl;

public interface URLTextureManager {
    static URLTextureManager getInstance() {
        return URLTextureManagerImpl.INSTANCE;
    }

    TextureLoadResult getAndAsyncLoad(String url, boolean cached);
}
