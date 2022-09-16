package dev.felnull.otyacraftengine.fabric;

import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.fabric.server.handler.ServerHandlerFabric;
import net.fabricmc.api.ModInitializer;

public class OtyacraftEngineFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        OtyacraftEngine.init();
        ServerHandlerFabric.init();
    }
}
