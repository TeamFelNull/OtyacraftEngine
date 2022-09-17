package dev.felnull.otyacraftengine.client.renderer.texture;

import dev.felnull.otyacraftengine.client.renderer.texture.impl.URLTextureManagerOldImpl;

public interface URLTextureManager {
    static URLTextureManager getInstance() {
        return URLTextureManagerOldImpl.INSTANCE;
    }

    TextureLoadResult getAndAsyncLoad(String url, boolean cached);
}
