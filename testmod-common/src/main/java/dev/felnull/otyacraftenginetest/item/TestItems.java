package dev.felnull.otyacraftenginetest.item;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.felnull.otyacraftenginetest.OtyacraftEngineTest;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class TestItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(OtyacraftEngineTest.MODID, Registry.ITEM_REGISTRY);
    public static final RegistrySupplier<Item> TEST_ITEM = register("test_item", () -> new TestItem(new Item.Properties().tab(TestCreativeTab.TEST_TAB)));
    public static final RegistrySupplier<Item> CODE_TEST = register("code_test", () -> new CodeTestItem(new Item.Properties().tab(TestCreativeTab.TEST_TAB)));
    public static final RegistrySupplier<Item> SAVE_DATA_TEST = register("save_data_test", () -> new TestSaveDataItem(new Item.Properties().tab(TestCreativeTab.TEST_TAB)));
    public static final RegistrySupplier<Item> EQUIPMENT_TEST = register("equipment_test", () -> new TestEquipmentItem(new Item.Properties().tab(TestCreativeTab.TEST_TAB)));
    public static final RegistrySupplier<Item> TEST_RENDERER_ITEM = register("test_renderer_item", () -> new Item(new Item.Properties().tab(TestCreativeTab.TEST_TAB)));

    private static RegistrySupplier<Item> register(String name, Supplier<Item> item) {
        return ITEMS.register(name, item);
    }

    public static void init() {
        ITEMS.register();
    }
}
