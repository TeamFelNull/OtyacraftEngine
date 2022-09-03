package dev.felnull.otyacraftengine.fabric.data.provider;

import dev.felnull.otyacraftengine.data.provider.BlockTagProviderWrapper;
import dev.felnull.otyacraftengine.data.provider.TagProviderWrapper;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class WrappedFabricBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    private final BlockTagProviderWrapper tagProviderWrapper;

    public WrappedFabricBlockTagProvider(FabricDataGenerator dataGenerator, BlockTagProviderWrapper tagProviderWrapper) {
        super(dataGenerator);
        this.tagProviderWrapper = tagProviderWrapper;
    }

    @Override
    protected void generateTags() {
        tagProviderWrapper.generateTag(new BlockTagProviderAccessImpl());
    }

    private class BlockTagProviderAccessImpl implements TagProviderWrapper.TagProviderAccess<Block> {
        @Override
        public TagProviderWrapper.TagAppenderWrapper<Block> tag(TagKey<Block> tagKey) {
            return new WrappedFabricTagProvider.TagAppenderWrapperImpl<>(WrappedFabricBlockTagProvider.this.tag(tagKey));
        }
    }
}
