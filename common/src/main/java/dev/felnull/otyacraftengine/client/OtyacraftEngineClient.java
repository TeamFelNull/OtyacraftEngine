package dev.felnull.otyacraftengine.client;

import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.client.handler.ClientHandler;
import dev.felnull.otyacraftengine.client.handler.TestClientHandler;
import dev.felnull.otyacraftengine.client.util.ClientUtilInit;
import net.minecraft.client.Minecraft;

public class OtyacraftEngineClient {
    private static final Minecraft mc = Minecraft.getInstance();

    public static void init() {
        ClientHandler.init();
        ClientUtilInit.init();

        if (OtyacraftEngine.CONFIG.testMode)
            testInit();
    }

    public static void testInit() {
        TestClientHandler.init();
    }

}
