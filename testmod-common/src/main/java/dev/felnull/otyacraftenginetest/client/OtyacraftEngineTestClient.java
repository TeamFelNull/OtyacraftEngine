package dev.felnull.otyacraftenginetest.client;

import dev.felnull.otyacraftenginetest.client.handler.ClientHandler;
import dev.felnull.otyacraftenginetest.client.renderer.blockentity.TestRenderers;

public class OtyacraftEngineTestClient {
    public static void init() {
        TestRenderers.init();
        ClientHandler.init();
    }
}
