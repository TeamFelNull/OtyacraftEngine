package dev.felnull.otyacraftenginetest.client.renderer.item;

import dev.felnull.otyacraftengine.client.renderer.item.ItemRendererRegister;
import dev.felnull.otyacraftenginetest.item.TestItems;

public class TestItemRenderers {
    public static void init() {
        ItemRendererRegister.register(TestItems.TEST_RENDERER_ITEM, new TestItemRenderer());
    }
}
