package dev.felnull.otyacraftenginetest.item;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.felnull.otyacraftenginetest.OtyacraftEngineTest;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class TestCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(OtyacraftEngineTest.MODID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> TEST_TAB = TABS.register(OtyacraftEngineTest.MODID, () -> CreativeTabRegistry.create(Component.literal("TEST Tab"), () -> new ItemStack(Items.APPLE)));

    public static void init() {
        TABS.register();
    }
}
