package dev.felnull.otyacraftengine.explatform.fabric;

import dev.felnull.otyacraftengine.fabric.tag.OEFabricItemTags;
import dev.felnull.otyacraftengine.tag.ManualTagHolder;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class OETagsExpectPlatformImpl {
    public static TagKey<Item> pickaxes() {
        return ConventionalItemTags.PICKAXES;
    }

    public static TagKey<Item> shovels() {
        return ConventionalItemTags.SHOVELS;
    }

    public static TagKey<Item> hoes() {
        return ConventionalItemTags.HOES;
    }

    public static TagKey<Item> axes() {
        return ConventionalItemTags.AXES;
    }

    public static TagKey<Item> shears() {
        return ConventionalItemTags.SHEARS;
    }

    public static TagKey<Item> swords() {
        return ConventionalItemTags.SWORDS;
    }

    public static TagKey<Item> bows() {
        return ConventionalItemTags.BOWS;
    }

    public static TagKey<Item> ironIngots() {
        return ConventionalItemTags.IRON_INGOTS;
    }

    public static TagKey<Item> goldIngots() {
        return ConventionalItemTags.GOLD_INGOTS;
    }

    public static TagKey<Item> copperIngots() {
        return ConventionalItemTags.COPPER_INGOTS;
    }

    public static TagKey<Item> netheriteIngots() {
        return ConventionalItemTags.NETHERITE_INGOTS;
    }

    public static TagKey<Item> redstoneDusts() {
        return ConventionalItemTags.REDSTONE_DUSTS;
    }

    public static TagKey<Item> diamonds() {
        return ConventionalItemTags.DIAMONDS;
    }

    public static TagKey<Item> glassBlocks() {
        return ConventionalItemTags.GLASS_BLOCKS;
    }

    public static TagKey<Item> glassPanes() {
        return ConventionalItemTags.GLASS_PANES;
    }

    public static TagKey<Item> books() {
        return OEFabricItemTags.BOOKS.get();
    }

    public static ManualTagHolder<Item> ironNuggets() {
        return OEFabricItemTags.IRON_NUGGETS.get();
    }

    public static ManualTagHolder<Item> enderPearls() {
        return OEFabricItemTags.ENDER_PEARLS.get();
    }

    public static ManualTagHolder<Item> stone() {
        return OEFabricItemTags.STONE.get();
    }

    public static ManualTagHolder<Item> redstoneBlocks() {
        return OEFabricItemTags.REDSTONE_BLOCKS.get();
    }
}
