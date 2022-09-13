package dev.felnull.otyacraftenginetest.client.model;

import dev.felnull.otyacraftengine.client.model.ModelHolder;
import dev.felnull.otyacraftengine.client.model.SimpleBaseModelBundle;
import dev.felnull.otyacraftenginetest.OtyacraftEngineTest;
import net.minecraft.resources.ResourceLocation;

public class TestModelBundle extends SimpleBaseModelBundle {
    public final ModelHolder TEST_1 = holder("test_1");
    public final ModelHolder TEST_2 = holder("test_2");
    public final ModelHolder TEST_3 = holder("test_3");

    protected ModelHolder holder(String name) {
        return holder(new ResourceLocation(OtyacraftEngineTest.MODID, "item/test_models/" + name));
    }
}
