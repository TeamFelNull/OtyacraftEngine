package dev.felnull.otyacraftengine.data.provider;

import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.Block;

public abstract class BlockTagProviderWrapper extends TagProviderWrapper<Block, TagProviderWrapper.TagProviderAccess<Block>> {
    private final TagsProvider<Block> blockTagsProvider;

    public BlockTagProviderWrapper(CrossDataGeneratorAccess crossDataGeneratorAccess) {
        super(crossDataGeneratorAccess);
        this.blockTagsProvider = crossDataGeneratorAccess.createBlockTagProvider(this);
    }

    @Override
    public TagsProvider<Block> getProvider() {
        return blockTagsProvider;
    }
}
