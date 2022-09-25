package dev.felnull.otyacraftengine.forge.data.provider;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import dev.felnull.otyacraftengine.data.provider.BlockLootTableProviderWrapper;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class WrappedBlockLootTableProvider extends LootTableProvider {
    private final BlockLootTableProviderWrapper blockLootTableProviderWrapper;
    private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> subProviders = ImmutableList.of(Pair.of(WrappedBlockLoot::new, LootContextParamSets.BLOCK));

    public WrappedBlockLootTableProvider(DataGenerator arg, BlockLootTableProviderWrapper blockLootTableProviderWrapper) {
        super(arg);
        this.blockLootTableProviderWrapper = blockLootTableProviderWrapper;
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return subProviders;
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
    }

    private class WrappedBlockLoot extends BlockLoot {
        @Override
        protected Iterable<Block> getKnownBlocks() {
            return blockLootTableProviderWrapper.getKnownBlocks();
        }

        @Override
        protected void addTables() {
            blockLootTableProviderWrapper.generateBlockLootTables(this, (b) -> {
            });
        }
    }
}
