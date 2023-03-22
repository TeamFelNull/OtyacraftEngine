package dev.felnull.otyacraftenginetest.item;

import dev.architectury.extensions.injected.InjectedItemPropertiesExtension;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.felnull.otyacraftenginetest.OtyacraftEngineTest;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.*;

import java.util.function.Supplier;

public class TestItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(OtyacraftEngineTest.MODID, Registries.ITEM);
    public static final RegistrySupplier<Item> TEST_ITEM = register("test_item", () -> new TestItem(tabWrap(new Item.Properties())));
    public static final RegistrySupplier<Item> TEST_RENDERER_ITEM = register("test_renderer_item", () -> new Item(tabWrap(new Item.Properties())));
    public static final RegistrySupplier<Item> TEST_RENDERER_ITEM2 = register("test_renderer_item2", () -> new Item(tabWrap(new Item.Properties())));
    public static final RegistrySupplier<Item> TEST_STACK_ATTRIBUTE_MODIFIER_ITEM = register("test_stack_attribute_modifier_item", () -> new TestStackAttributeModifierItem(tabWrap(new Item.Properties())));

    public static final RegistrySupplier<Item> TEST_SWORD = register("test_sword", () -> new SwordItem(Tiers.IRON, 3, -2.4F, tabWrap(new Item.Properties())));
    public static final RegistrySupplier<Item> TEST_HELMET = register("test_helmet", () -> new ArmorItem(ArmorMaterials.IRON, ArmorItem.Type.HELMET, tabWrap(new Item.Properties())));
    public static final RegistrySupplier<Item> TEST_CHESTPLATE = register("test_chestplate", () -> new ArmorItem(ArmorMaterials.IRON, ArmorItem.Type.CHESTPLATE, tabWrap(new Item.Properties())));
    public static final RegistrySupplier<Item> TEST_LEGGINGS = register("test_leggings", () -> new ArmorItem(ArmorMaterials.IRON, ArmorItem.Type.LEGGINGS, tabWrap(new Item.Properties())));
    public static final RegistrySupplier<Item> TEST_BOOTS = register("test_boots", () -> new ArmorItem(ArmorMaterials.IRON, ArmorItem.Type.BOOTS, tabWrap(new Item.Properties())));

    /*
   public static final RegistrySupplier<Item> CODE_TEST = register("code_test", () -> new CodeTestItem(new Item.Properties().tab(TestCreativeTab.TEST_TAB)));
   public static final RegistrySupplier<Item> SAVE_DATA_TEST = register("save_data_test", () -> new TestSaveDataItem(new Item.Properties().tab(TestCreativeTab.TEST_TAB)));
   public static final RegistrySupplier<Item> EQUIPMENT_TEST = register("equipment_test", () -> new TestEquipmentItem(new Item.Properties().tab(TestCreativeTab.TEST_TAB)));
*/
    private static RegistrySupplier<Item> register(String name, Supplier<Item> item) {
        return ITEMS.register(name, item);
    }

    public static void init() {
        ITEMS.register();
    }

    private static Item.Properties tabWrap(Item.Properties properties) {
        return ((InjectedItemPropertiesExtension) properties).arch$tab(TestCreativeTab.TEST_TAB);
    }
}
