package net.examplemod.item;

import dev.architectury.registry.CreativeTabRegistry;
import net.examplemod.ExampleMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class TestCreativeTab {
    public static final CreativeModeTab TEST_TAB = CreativeTabRegistry.create(new ResourceLocation(ExampleMod.MODID, "test_tab"), () -> new ItemStack(Items.APPLE));
}
