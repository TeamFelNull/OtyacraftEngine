package dev.felnull.otyacraftengine.client;

import dev.felnull.otyacraftengine.client.handler.ClientDebugHandler;
import dev.felnull.otyacraftengine.client.handler.ClientHandler;
import dev.felnull.otyacraftengine.client.renderer.texture.URLTextureManager;

public class OtyacraftEngineClient {

    public static void init() {
        ClientDebugHandler.init();
        ClientHandler.init();
        URLTextureManager.getInstance().init();
    }
}
