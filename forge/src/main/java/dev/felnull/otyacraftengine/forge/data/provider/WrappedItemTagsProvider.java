package dev.felnull.otyacraftengine.forge.data.provider;

import dev.felnull.otyacraftengine.data.provider.ItemTagProviderWrapper;
import dev.felnull.otyacraftengine.data.provider.TagProviderWrapper;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WrappedItemTagsProvider extends ItemTagsProvider {
    private final ItemTagProviderWrapper tagProviderWrapper;

    public WrappedItemTagsProvider(DataGenerator arg, @NotNull BlockTagsProvider arg2, String modId, @Nullable ExistingFileHelper existingFileHelper, ItemTagProviderWrapper tagProviderWrapper) {
        super(arg, arg2, modId, existingFileHelper);
        this.tagProviderWrapper = tagProviderWrapper;

    }

    @Override
    protected void addTags() {
        this.tagProviderWrapper.generateTag(new ItemTagProviderAccessImpl());
    }

    private class ItemTagProviderAccessImpl implements ItemTagProviderWrapper.ItemTagProviderAccess {
        @Override
        public void copy(TagKey<Block> blockTag, TagKey<Item> itemTag) {
            WrappedItemTagsProvider.this.copy(blockTag, itemTag);
        }

        @Override
        public TagProviderWrapper.TagAppenderWrapper<Item> tag(TagKey<Item> tagKey) {
            return new WrappedTagsProvider.TagAppenderWrapperImpl<>(WrappedItemTagsProvider.this.tag(tagKey));
        }
    }
}
