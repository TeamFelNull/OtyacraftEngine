package dev.felnull.otyacraftengine.forge.data.provider;

import dev.felnull.otyacraftengine.data.provider.BlockTagProviderWrapper;
import dev.felnull.otyacraftengine.data.provider.TagProviderWrapper;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class WrappedBlockTagProvider extends BlockTagsProvider {
    private final BlockTagProviderWrapper tagProviderWrapper;

    public WrappedBlockTagProvider(DataGenerator arg, String modId, @Nullable ExistingFileHelper existingFileHelper, BlockTagProviderWrapper tagProviderWrapper) {
        super(arg, modId, existingFileHelper);
        this.tagProviderWrapper = tagProviderWrapper;
    }

    @Override
    protected void addTags() {
        tagProviderWrapper.generateTag(new BlockTagProviderAccessImpl());
    }

    private class BlockTagProviderAccessImpl implements TagProviderWrapper.TagProviderAccess<Block> {
        @Override
        public TagProviderWrapper.TagAppenderWrapper<Block> tag(TagKey<Block> tagKey) {
            return new WrappedTagProvider.TagAppenderWrapperImpl<>(WrappedBlockTagProvider.this.tag(tagKey));
        }
    }
}
