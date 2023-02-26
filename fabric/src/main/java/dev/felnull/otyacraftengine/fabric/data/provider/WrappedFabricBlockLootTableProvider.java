package dev.felnull.otyacraftengine.fabric.data.provider;

import dev.felnull.otyacraftengine.data.provider.BlockLootTableProviderWrapper;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.function.BiConsumer;

public class WrappedFabricBlockLootTableProvider extends FabricBlockLootTableProvider {
    private final BlockLootTableProviderWrapper blockLootTableProviderWrapper;

    public WrappedFabricBlockLootTableProvider(FabricDataOutput dataOutput, BlockLootTableProviderWrapper blockLootTableProviderWrapper) {
        super(dataOutput);
        this.blockLootTableProviderWrapper = blockLootTableProviderWrapper;
    }

    @Override
    public void generate() {
        blockLootTableProviderWrapper.generateBlockLootTables(this, new BlockLootTableProviderAccessImpl());
    }

    private class BlockLootTableProviderAccessImpl implements BlockLootTableProviderWrapper.BlockLootTableProviderAccess {
        @Override
        public void excludeFromStrictValidation(Block block) {
            WrappedFabricBlockLootTableProvider.this.excludeFromStrictValidation(block);
        }

        @Override
        public void dropSelf(Block block) {
            WrappedFabricBlockLootTableProvider.this.dropSelf(block);
        }

        @Override
        public void dropOther(Block block, ItemLike itemLike) {
            WrappedFabricBlockLootTableProvider.this.dropOther(block, itemLike);
        }

        @Override
        public void add(Block block, LootTable.Builder builder) {
            WrappedFabricBlockLootTableProvider.this.add(block, builder);
        }
    }

    @Override
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> resourceLocationBuilderBiConsumer) {
        generate(resourceLocationBuilderBiConsumer);
    }
}
