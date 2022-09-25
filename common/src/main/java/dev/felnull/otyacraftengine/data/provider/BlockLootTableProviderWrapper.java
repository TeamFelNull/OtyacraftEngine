package dev.felnull.otyacraftengine.data.provider;

import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import dev.felnull.otyacraftengine.data.DataGeneratorType;
import net.minecraft.data.DataProvider;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;

public abstract class BlockLootTableProviderWrapper extends DataProviderWrapper<DataProvider> {
    private final DataProvider blockLootTableProvider;

    public BlockLootTableProviderWrapper(CrossDataGeneratorAccess crossDataGeneratorAccess) {
        super(crossDataGeneratorAccess);
        this.blockLootTableProvider = crossDataGeneratorAccess.createBlockLootTableProvider(this);
    }

    @Override
    public DataProvider getProvider() {
        return blockLootTableProvider;
    }

    @Override
    public DataGeneratorType getGeneratorType() {
        return DataGeneratorType.SERVER;
    }

    public abstract void generateBlockLootTables(BlockLoot blockLoot, BlockLootTableProviderAccess providerAccess);

    public abstract Iterable<Block> getKnownBlocks();

    public static interface BlockLootTableProviderAccess {
        void excludeFromStrictValidation(Block block);
    }
}
