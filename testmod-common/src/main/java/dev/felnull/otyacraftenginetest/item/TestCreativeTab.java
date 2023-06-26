package dev.felnull.otyacraftenginetest.item;

import dev.architectury.registry.CreativeTabRegistry;
import dev.felnull.otyacraftenginetest.OtyacraftEngineTest;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class TestCreativeTab {
    public static final CreativeModeTab TEST_TAB = CreativeTabRegistry.create(Component.literal("TEST Tab")/*new ResourceLocation(OtyacraftEngineTest.MODID, "test_tab")*/, () -> new ItemStack(Items.APPLE));
    // public static final CreativeModeTab TEST_TAB = CreativeTabRegistry.create(new ResourceLocation(OtyacraftEngineTest.MODID, "test_tab"), () -> new ItemStack(Items.APPLE));
}
