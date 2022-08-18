package dev.felnull.otyacraftenginetest.block;

import dev.felnull.otyacraftengine.blockentity.OEBaseBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TestBlockEntityBlock extends OEBaseBlockEntity {
    public TestBlockEntityBlock(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }
}
