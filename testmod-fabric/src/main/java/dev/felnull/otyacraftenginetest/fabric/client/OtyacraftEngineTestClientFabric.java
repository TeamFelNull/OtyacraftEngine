package dev.felnull.otyacraftenginetest.fabric.client;

import dev.felnull.otyacraftenginetest.client.OtyacraftEngineTestClient;
import net.fabricmc.api.ClientModInitializer;

public class OtyacraftEngineTestClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        OtyacraftEngineTestClient.init();
    }
}
