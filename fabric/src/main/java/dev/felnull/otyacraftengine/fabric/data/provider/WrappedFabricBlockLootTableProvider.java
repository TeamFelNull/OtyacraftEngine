package dev.felnull.otyacraftengine.fabric.data.provider;

import dev.felnull.otyacraftengine.data.provider.BlockLootTableProviderWrapper;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

public class WrappedFabricBlockLootTableProvider extends FabricBlockLootTableProvider {
    private final BlockLootTableProviderWrapper blockLootTableProviderWrapper;

    public WrappedFabricBlockLootTableProvider(FabricDataGenerator dataGenerator, BlockLootTableProviderWrapper blockLootTableProviderWrapper) {
        super(dataGenerator);
        this.blockLootTableProviderWrapper = blockLootTableProviderWrapper;
    }

    @Override
    protected void generateBlockLootTables() {
        blockLootTableProviderWrapper.generateBlockLootTables(this, WrappedFabricBlockLootTableProvider.this::excludeFromStrictValidation);
    }
}
