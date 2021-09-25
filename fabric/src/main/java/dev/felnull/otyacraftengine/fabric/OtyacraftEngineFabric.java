package dev.felnull.otyacraftengine.fabric;

import dev.felnull.otyacraftengine.OtyacraftEngine;
import net.fabricmc.api.ModInitializer;

public class OtyacraftEngineFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        OtyacraftEngine.init();
    }
}
