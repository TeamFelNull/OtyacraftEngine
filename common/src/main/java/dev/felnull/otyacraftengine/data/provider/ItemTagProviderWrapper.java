package dev.felnull.otyacraftengine.data.provider;

import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public abstract class ItemTagProviderWrapper extends TagProviderWrapper<Item, ItemTagProviderWrapper.ItemTagProviderAccess> {
    private final TagsProvider<Item> itemTagsProvider;

    public ItemTagProviderWrapper(CrossDataGeneratorAccess crossDataGeneratorAccess, @NotNull BlockTagProviderWrapper blockTagProviderWrapper) {
        super(crossDataGeneratorAccess);
        this.itemTagsProvider = crossDataGeneratorAccess.createItemTagProvider(this, blockTagProviderWrapper);
    }

    @Override
    public TagsProvider<Item> getProvider() {
        return this.itemTagsProvider;
    }

    public static interface ItemTagProviderAccess extends TagProviderAccess<Item> {
        void copy(TagKey<Block> blockTag, TagKey<Item> itemTag);
    }
}
