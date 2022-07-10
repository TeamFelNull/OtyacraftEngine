package net.examplemod.client.model;

import dev.felnull.otyacraftengine.client.callpoint.ModelRegister;
import net.examplemod.ExampleMod;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class TestModels {
    public static Supplier<BakedModel> KAMESUTA_MODEL;
    public static Supplier<BakedModel> KAMESUTA_ANTENNA_MODEL;
    public static Supplier<BakedModel> SEA_CHICKEN_MODEL;

    public static void init(ModelRegister register) {
        KAMESUTA_MODEL = register.addAndGetModel(new ResourceLocation(ExampleMod.MODID, "block/test_model"));
        KAMESUTA_ANTENNA_MODEL = register.addAndGetModel(new ResourceLocation(ExampleMod.MODID, "item/kamesuta_antenna"));
        SEA_CHICKEN_MODEL = register.addAndGetModel(new ResourceLocation(ExampleMod.MODID, "item/sea_chicken"));
    }
}
