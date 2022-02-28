package dev.felnull.otyacraftengine.client;

import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.client.handler.ClientHandler;
import dev.felnull.otyacraftengine.client.handler.TestClientHandler;
import dev.felnull.otyacraftengine.client.renderer.blockentity.TestRenderer;
import dev.felnull.otyacraftengine.client.renderer.item.TestItemRenderer;
import dev.felnull.otyacraftengine.client.util.ClientUtilInit;
import dev.felnull.otyacraftengine.networking.OEPackets;

public class OtyacraftEngineClient {

    public static void init() {
        ClientHandler.init();
        ClientUtilInit.init();

        if (OtyacraftEngine.CONFIG.testMode)
            testInit();

        OEPackets.clientInit();
    }

    public static void testInit() {
        TestClientHandler.init();
        TestRenderer.init();
        TestItemRenderer.init();
    }
}
