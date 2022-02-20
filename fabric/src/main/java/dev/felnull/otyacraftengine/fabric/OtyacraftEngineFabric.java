package dev.felnull.otyacraftengine.fabric;

import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.fabric.server.handler.ServerHandler;
import net.fabricmc.api.ModInitializer;

public class OtyacraftEngineFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ServerHandler.init();
        OtyacraftEngine.init();
    }
}
