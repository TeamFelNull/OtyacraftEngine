package dev.felnull.otyacraftengine.fabric.data.provider;

import dev.felnull.otyacraftengine.data.provider.ItemTagProviderWrapper;
import dev.felnull.otyacraftengine.data.provider.TagProviderWrapper;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class WrappedFabricItemTagProvider extends FabricTagProvider.ItemTagProvider {
    private final ItemTagProviderWrapper tagProviderWrapper;

    public WrappedFabricItemTagProvider(FabricDataGenerator dataGenerator, @NotNull BlockTagProvider blockTagProvider, ItemTagProviderWrapper tagProviderWrapper) {
        super(dataGenerator, blockTagProvider);
        this.tagProviderWrapper = tagProviderWrapper;
    }

    @Override
    protected void generateTags() {
        this.tagProviderWrapper.generateTag(new ItemTagProviderAccessImpl());
    }

    private class ItemTagProviderAccessImpl implements ItemTagProviderWrapper.ItemTagProviderAccess {
        @Override
        public void copy(TagKey<Block> blockTag, TagKey<Item> itemTag) {
            WrappedFabricItemTagProvider.this.copy(blockTag, itemTag);
        }

        @Override
        public TagProviderWrapper.TagAppenderWrapper<Item> tag(TagKey<Item> tagKey) {
            return new WrappedFabricTagProvider.TagAppenderWrapperImpl<>(WrappedFabricItemTagProvider.this.tag(tagKey));
        }
    }
}
