package dev.felnull.otyacraftengine.forge.tag;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class ForgeItemTags {
    public static final Supplier<TagKey<Item>> BOOKS = reg("books");

    private static Supplier<TagKey<Item>> reg(String name) {
        return () -> ItemTags.create(new ResourceLocation("forge", name));
    }
}
