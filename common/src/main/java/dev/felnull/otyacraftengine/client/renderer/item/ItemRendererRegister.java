package dev.felnull.otyacraftengine.client.renderer.item;

import dev.felnull.otyacraftengine.impl.client.OEClientExpectPlatform;
import net.minecraft.world.level.ItemLike;

public class ItemRendererRegister {
    public static void register(ItemLike item, BEWLItemRenderer renderer) {
        OEClientExpectPlatform.registerItemRenderer(item, renderer);
    }
}
