package dev.felnull.otyacraftengine.client;

import dev.felnull.otyacraftengine.client.handler.ClientHandler;
import dev.felnull.otyacraftengine.client.handler.TestClientHandler;

public class OtyacraftEngineClient {
    public static void init() {
        ClientHandler.init();

        testInit();
    }

    public static void testInit() {
        TestClientHandler.init();
    }

}
