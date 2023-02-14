package dev.felnull.otyacraftenginetest.data;

import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import dev.felnull.otyacraftengine.data.provider.BlockStateAndModelProviderWrapper;
import dev.felnull.otyacraftenginetest.block.TestBlocks;
import net.minecraft.data.PackOutput;

public class OEBlockStateAndModelProviderWrapper extends BlockStateAndModelProviderWrapper {
    public OEBlockStateAndModelProviderWrapper(PackOutput packOutput, CrossDataGeneratorAccess crossDataGeneratorAccess) {
        super(packOutput, crossDataGeneratorAccess);
    }

    @Override
    public void generateStatesAndModels(BlockStateAndModelProviderAccess providerAccess) {
        providerAccess.simpleCubeBlockAndItem(TestBlocks.TEST_BLOCK.get());
    }
}
