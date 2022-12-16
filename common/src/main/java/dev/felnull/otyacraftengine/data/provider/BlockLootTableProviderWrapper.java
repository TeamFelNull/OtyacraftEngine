package dev.felnull.otyacraftengine.data.provider;

import dev.architectury.registry.registries.DeferredRegister;
import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

public abstract class BlockLootTableProviderWrapper extends DataProviderWrapper<DataProvider> {
    private final DataProvider blockLootTableProvider;

    public BlockLootTableProviderWrapper(PackOutput packOutput, CrossDataGeneratorAccess crossDataGeneratorAccess) {
        super(packOutput, crossDataGeneratorAccess);
        this.blockLootTableProvider = crossDataGeneratorAccess.createBlockLootTableProvider(this);
    }

    @Override
    public DataProvider getProvider() {
        return blockLootTableProvider;
    }


    public abstract void generateBlockLootTables(BlockLootSubProvider blockLoot, BlockLootTableProviderAccess providerAccess);

    public abstract Iterable<Block> getKnownBlocks();

    protected Iterable<Block> extract(DeferredRegister<Block> deferredRegister) {
        List<Block> blocks = new ArrayList<>();
        deferredRegister.iterator().forEachRemaining(r -> blocks.add(r.get()));
        return blocks;
    }

    public static interface BlockLootTableProviderAccess {
        void excludeFromStrictValidation(Block block);
    }
}
