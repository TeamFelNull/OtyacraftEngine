package net.examplemod.item;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.examplemod.ExampleMod;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;

public class TestItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ExampleMod.MODID, Registry.ITEM_REGISTRY);
    public static final RegistrySupplier<Item> TEST_ITEM = ITEMS.register("test_item", () -> new TestItem(new Item.Properties().tab(TestCreativeTab.TEST_TAB)));

    public static void init() {
        ITEMS.register();
    }
}
