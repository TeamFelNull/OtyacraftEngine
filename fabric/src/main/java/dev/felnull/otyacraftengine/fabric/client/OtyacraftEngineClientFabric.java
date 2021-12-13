package dev.felnull.otyacraftengine.fabric.client;

import dev.felnull.otyacraftengine.client.OtyacraftEngineClient;
import dev.felnull.otyacraftengine.fabric.client.handler.ModelResourceHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;

public class OtyacraftEngineClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        OtyacraftEngineClient.init();
        ModelResourceHandler.init();
    }
}
