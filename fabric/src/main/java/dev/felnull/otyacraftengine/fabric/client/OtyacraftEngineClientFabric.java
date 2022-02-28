package dev.felnull.otyacraftengine.fabric.client;

import dev.felnull.otyacraftengine.client.OtyacraftEngineClient;
import dev.felnull.otyacraftengine.fabric.client.handler.ModelResourceHandler;
import net.fabricmc.api.ClientModInitializer;

public class OtyacraftEngineClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        OtyacraftEngineClient.init();
        ModelResourceHandler.init();
    }
}
