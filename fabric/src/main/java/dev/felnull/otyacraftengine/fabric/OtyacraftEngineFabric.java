package dev.felnull.otyacraftengine.fabric;

import dev.felnull.otyacraftengine.OtyacraftEngine;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;

public class OtyacraftEngineFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        OtyacraftEngine.init();
    }
}
