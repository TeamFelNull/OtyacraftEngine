package dev.felnull.otyacraftengine.client;

import dev.felnull.otyacraftengine.client.handler.ClientHandler;
import dev.felnull.otyacraftengine.client.handler.TestClientHandler;
import dev.felnull.otyacraftengine.client.util.ClientUtilInit;

public class OtyacraftEngineClient {
    public static void init() {
        ClientHandler.init();
        ClientUtilInit.init();
        testInit();
    }

    public static void testInit() {
        TestClientHandler.init();
    }

}
