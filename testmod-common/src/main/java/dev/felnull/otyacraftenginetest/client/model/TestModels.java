package dev.felnull.otyacraftenginetest.client.model;

import dev.felnull.otyacraftengine.client.callpoint.ModelRegister;
import dev.felnull.otyacraftengine.client.model.ModelDeferredRegister;
import dev.felnull.otyacraftengine.client.model.ModelHolder;
import dev.felnull.otyacraftenginetest.OtyacraftEngineTest;
import net.minecraft.resources.ResourceLocation;

public class TestModels {
    private static final ModelDeferredRegister REGISTER = ModelDeferredRegister.create();
    public static ModelHolder KAMESUTA_MODEL = REGISTER.register(new ResourceLocation(OtyacraftEngineTest.MODID, "block/test_model"));
    public static ModelHolder KAMESUTA_ANTENNA_MODEL = REGISTER.register(new ResourceLocation(OtyacraftEngineTest.MODID, "item/kamesuta_antenna"));
    public static ModelHolder SEA_CHICKEN_MODEL = REGISTER.register(new ResourceLocation(OtyacraftEngineTest.MODID, "item/sea_chicken"));
    public static TestModelBundle TEST_MODELS = REGISTER.register(new TestModelBundle());

    public static void init(ModelRegister register) {
        REGISTER.registering(register);
    }
}
