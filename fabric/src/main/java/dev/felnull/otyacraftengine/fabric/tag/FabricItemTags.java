package dev.felnull.otyacraftengine.fabric.tag;

import dev.felnull.fnjl.util.FNDataUtil;
import dev.felnull.otyacraftengine.data.provider.TagProviderWrapper;
import dev.felnull.otyacraftengine.tag.ManualRegistrationTag;
import net.fabricmc.fabric.impl.tag.convention.TagRegistration;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class FabricItemTags {
    public static final Supplier<ManualRegistrationTag<Item>> IRON_NUGGETS = reg("iron_nuggets", tp -> tp.add(Items.IRON_NUGGET));
    public static final Supplier<ManualRegistrationTag<Item>> ENDER_PEARLS = reg("ender_pearls", tp -> tp.add(Items.ENDER_PEARL));
    public static final Supplier<ManualRegistrationTag<Item>> STONE = reg("stone", tp -> {
        tp.add(Items.STONE, Items.ANDESITE, Items.DIORITE, Items.GRANITE, Items.DEEPSLATE);
        tp.add(Items.POLISHED_ANDESITE, Items.POLISHED_DIORITE, Items.POLISHED_GRANITE, Items.POLISHED_DEEPSLATE);
    });
    public static final Supplier<ManualRegistrationTag<Item>> REDSTONE_BLOCKS = reg("redstone_blocks", tp -> tp.add(Items.REDSTONE_BLOCK));
    public static final Supplier<TagKey<Item>> BOOKS = reg("books");

    private static Supplier<ManualRegistrationTag<Item>> reg(String name, Consumer<TagProviderWrapper.TagAppenderWrapper<Item>> tagRegister) {
        return FNDataUtil.memoize(() -> new ManualRegistrationTag<>(TagRegistration.ITEM_TAG_REGISTRATION.registerCommon(name), tagRegister));
    }

    private static Supplier<TagKey<Item>> reg(String name) {
        return () -> TagRegistration.ITEM_TAG_REGISTRATION.registerCommon(name);
    }
}
