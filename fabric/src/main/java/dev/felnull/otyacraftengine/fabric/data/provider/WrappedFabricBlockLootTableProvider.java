package dev.felnull.otyacraftengine.fabric.data.provider;

import dev.felnull.otyacraftengine.data.provider.BlockLootTableProviderWrapper;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.function.BiConsumer;

public class WrappedFabricBlockLootTableProvider extends FabricBlockLootTableProvider {
    private final BlockLootTableProviderWrapper blockLootTableProviderWrapper;

    protected WrappedFabricBlockLootTableProvider(FabricDataOutput dataOutput, BlockLootTableProviderWrapper blockLootTableProviderWrapper) {
        super(dataOutput);
        this.blockLootTableProviderWrapper = blockLootTableProviderWrapper;
    }

    @Override
    public void generate() {
        blockLootTableProviderWrapper.generateBlockLootTables(this, WrappedFabricBlockLootTableProvider.this::excludeFromStrictValidation);
    }

    @Override
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> resourceLocationBuilderBiConsumer) {
        generate(resourceLocationBuilderBiConsumer);
    }
}
