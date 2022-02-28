package dev.felnull.otyacraftengine.client.entrypoint;

import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.client.model.ModelRegister;
import net.minecraft.resources.ResourceLocation;

@OEClientEntryPoint
public class MyOEClientEntryPoint implements IOEClientEntryPoint {
    @Override
    public void onModelRegistry(ModelRegister register) {
        register.registerLoadModel(new ResourceLocation(OtyacraftEngine.MODID, "block/test_model"));
    }
}
