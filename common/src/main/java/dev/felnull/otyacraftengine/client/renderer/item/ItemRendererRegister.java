package dev.felnull.otyacraftengine.client.renderer.item;

import dev.architectury.registry.registries.RegistrySupplier;
import dev.felnull.otyacraftengine.explatform.client.OEClientExpectPlatform;
import net.minecraft.world.level.ItemLike;

public class ItemRendererRegister {
    public static void register(RegistrySupplier<? extends ItemLike> item, BEWLItemRenderer renderer) {
        OEClientExpectPlatform.registerItemRenderer(item.get(), renderer);
    }

    public static void register(ItemLike item, BEWLItemRenderer renderer) {
        OEClientExpectPlatform.registerItemRenderer(item, renderer);
    }
}
