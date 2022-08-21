package dev.felnull.otyacraftenginetest.client;

import dev.felnull.otyacraftenginetest.client.handler.ClientHandler;
import dev.felnull.otyacraftenginetest.client.renderer.blockentity.TestBlockEntityRenderers;
import dev.felnull.otyacraftenginetest.client.renderer.item.TestItemRenderers;

public class OtyacraftEngineTestClient {
    public static void init() {
        TestBlockEntityRenderers.init();
        TestItemRenderers.init();
        ClientHandler.init();
    }
}
