package dev.felnull.otyacraftengine.data.provider;

import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;

public abstract class BlockStateAndModelProviderWrapper extends DataProviderWrapper<DataProvider> {
    private final DataProvider blockStateAndModelProvider;

    public BlockStateAndModelProviderWrapper(PackOutput packOutput, CrossDataGeneratorAccess crossDataGeneratorAccess) {
        super(packOutput, crossDataGeneratorAccess);
        this.blockStateAndModelProvider = crossDataGeneratorAccess.createBlockStateAndModelProvider(packOutput, this);
    }

    @Override
    public DataProvider getProvider() {
        return blockStateAndModelProvider;
    }

    public abstract void generateStatesAndModels(BlockStateAndModelProviderAccess providerAccess);

    public static interface BlockStateAndModelProviderAccess {
        void simpleCubeBlockAndItem(Block block);
    }
}
