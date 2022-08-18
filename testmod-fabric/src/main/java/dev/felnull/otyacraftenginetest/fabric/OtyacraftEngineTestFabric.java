package dev.felnull.otyacraftenginetest.fabric;

import dev.felnull.otyacraftenginetest.OtyacraftEngineTest;
import net.fabricmc.api.ModInitializer;

public class OtyacraftEngineTestFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        OtyacraftEngineTest.init();
    }
}
