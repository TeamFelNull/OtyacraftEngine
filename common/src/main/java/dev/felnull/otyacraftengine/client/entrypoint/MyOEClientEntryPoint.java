package dev.felnull.otyacraftengine.client.entrypoint;

import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.client.model.OETestModels;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

@OEClientEntryPoint
public class MyOEClientEntryPoint implements IOEClientEntryPoint {

    @Override
    public void onModelRegistry(Consumer<ResourceLocation> add) {
        if (!OtyacraftEngine.isTestMode()) return;
        add.accept(new ResourceLocation(OtyacraftEngine.MODID, "block/test_model"));
        add.accept(new ResourceLocation(OtyacraftEngine.MODID, "item/test_item_kame"));

        OETestModels.init(add);
    }

    @Override
    public void onLayerRegistry(LayerRegister register) {
        if (!OtyacraftEngine.isTestMode()) return;

        //register.addLayer(EntityType.PIG, TestLayer::new);
       // register.addLayer(EntityType.PLAYER, TestLayer::new);
    }
}
