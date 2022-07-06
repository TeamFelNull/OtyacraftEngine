package dev.felnull.otyacraftengine.client;

import dev.felnull.otyacraftengine.OEConfig;
import dev.felnull.otyacraftengine.client.handler.ClientDebugHandler;
import dev.felnull.otyacraftengine.client.handler.ClientHandler;
import dev.felnull.otyacraftengine.client.renderer.texture.URLTextureManager;
import dev.felnull.otyacraftengine.networking.OEPackets;
import net.minecraft.client.renderer.RenderStateShard;

public class OtyacraftEngineClient {

    public static void init() {
        OEConfig.clientInit();
        ClientDebugHandler.init();
        ClientHandler.init();
        URLTextureManager.getInstance().init();

        OEPackets.clientInit();


    }
}
