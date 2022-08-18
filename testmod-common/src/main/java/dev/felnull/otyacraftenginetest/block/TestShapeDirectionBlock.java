package dev.felnull.otyacraftenginetest.block;

import dev.felnull.otyacraftengine.block.HorizontalDirectionalBaseBlock;
import dev.felnull.otyacraftengine.shape.bundle.DirectionVoxelShapesBundle;
import dev.felnull.otyacraftengine.util.OEVoxelShapeUtils;
import dev.felnull.otyacraftenginetest.OtyacraftEngineTest;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TestShapeDirectionBlock extends HorizontalDirectionalBaseBlock {
    private static final DirectionVoxelShapesBundle SHAPE = OEVoxelShapeUtils.makeAllDirection(OEVoxelShapeUtils.getShapeFromResource(new ResourceLocation(OtyacraftEngineTest.MODID, "test2"), TestShapeBlock.class));

    public TestShapeDirectionBlock(Properties properties) {
        super(properties);
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE.getShape(blockState.getValue(FACING));
    }
}
