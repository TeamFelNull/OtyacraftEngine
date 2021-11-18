package dev.felnull.otyacraftengine.client.model;

import dev.felnull.otyacraftengine.OtyacraftEngine;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SpecialModelLoader {
    private static final SpecialModelLoader INSTANCE = new SpecialModelLoader();
    private final Map<ResourceLocation, BakedModel> MODELS = new HashMap<>();
    private static final Minecraft mc = Minecraft.getInstance();

    public static SpecialModelLoader getInstance() {
        return INSTANCE;
    }

    public Supplier<BakedModel> registerLoadModel(ResourceLocation location) {
        MODELS.put(location, null);
        return () -> MODELS.get(location);
    }

    public BakedModel getModel(ResourceLocation resourceLocation) {

        //   if (!MODELS.containsKey(resourceLocation))
        //       throw new IllegalStateException("No unregistered load model");
        //   var mdl = MODELS.get(resourceLocation);
        //return /* mdl != null ? mdl :*/ mc.getModelManager().getMissingModel();
        return mc.getModelManager().getModel(new ModelResourceLocation(resourceLocation, "inventory"));
    }

    public synchronized void load(Consumer<ModelResourceLocation> load) {
        List<ResourceLocation> loads = new ArrayList<>(MODELS.keySet());
        MODELS.clear();
        load.accept(new ModelResourceLocation(OtyacraftEngine.MODID, "test_model", "inventory"));
        // loads.forEach(n -> load.accept(new ModelResourceLocation(n, "inventory")));
        // loads.forEach(n -> MODELS.put(n, bakery.bake(n, BlockModelRotation.X0_Y0)));
    }
}
