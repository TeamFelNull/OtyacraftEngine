package dev.felnull.otyacraftengine.client.model;

import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.impl.client.OEClientExpectPlatform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class SpecialModelLoader {
    private static final SpecialModelLoader INSTANCE = new SpecialModelLoader();
    private final List<ModelResourceLocation> LOCATIONS = new ArrayList<>();
    private static final Minecraft mc = Minecraft.getInstance();

    public static SpecialModelLoader getInstance() {
        return INSTANCE;
    }

    public Supplier<BakedModel> registerLoadModel(ResourceLocation location) {
        LOCATIONS.add(new ModelResourceLocation(location, OtyacraftEngine.MODID + "_default"));
        return () -> getModel(location);
    }

    public BakedModel getModel(ResourceLocation resourceLocation) {
        return mc.getModelManager().getModel(new ModelResourceLocation(resourceLocation, OtyacraftEngine.MODID + "_default"));
    }

    public synchronized void load(ModelBakery bakery) {
        LOCATIONS.forEach(n -> OEClientExpectPlatform.bakeryLoadTopLevel(bakery, n));
    }
}
