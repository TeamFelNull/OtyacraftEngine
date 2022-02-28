package dev.felnull.otyacraftengine.client.entrypoint;

import dev.felnull.otyacraftengine.OtyacraftEngine;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

@OEClientEntryPoint
public class MyOEClientEntryPoint implements IOEClientEntryPoint {
    @Override
    public void onModelRegistry(Consumer<ResourceLocation> add) {
        add.accept(new ResourceLocation(OtyacraftEngine.MODID, "block/test_model"));
    }
}
