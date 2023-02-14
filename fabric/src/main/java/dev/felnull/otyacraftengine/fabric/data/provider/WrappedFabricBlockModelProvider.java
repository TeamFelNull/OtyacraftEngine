package dev.felnull.otyacraftengine.fabric.data.provider;

import dev.felnull.otyacraftengine.data.provider.BlockStateAndModelProviderWrapper;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.world.level.block.Block;

public class WrappedFabricBlockModelProvider extends FabricModelProvider {
    private final BlockStateAndModelProviderWrapper blockStateAndModelProviderWrapper;

    public WrappedFabricBlockModelProvider(FabricDataOutput output, BlockStateAndModelProviderWrapper blockStateAndModelProviderWrapper) {
        super(output);
        this.blockStateAndModelProviderWrapper = blockStateAndModelProviderWrapper;
    }

    @Override
    public String getName() {
        return "Model Definitions (Block)";
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        this.blockStateAndModelProviderWrapper.generateStatesAndModels(new BlockStateAndModelProviderAccessImpl(blockStateModelGenerator));
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {

    }

    private static class BlockStateAndModelProviderAccessImpl implements BlockStateAndModelProviderWrapper.BlockStateAndModelProviderAccess {
        private final BlockModelGenerators blockModelGenerators;

        private BlockStateAndModelProviderAccessImpl(BlockModelGenerators blockModelGenerators) {
            this.blockModelGenerators = blockModelGenerators;
        }

        @Override
        public void simpleCubeBlockAndItem(Block block) {
            this.blockModelGenerators.createTrivialCube(block);
        }
    }
}
