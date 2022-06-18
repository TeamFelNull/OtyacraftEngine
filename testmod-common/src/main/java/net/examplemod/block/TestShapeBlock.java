package net.examplemod.block;

import dev.felnull.otyacraftengine.shape.bundle.DirectionVoxelShapesBundle;
import dev.felnull.otyacraftengine.util.OEVoxelShapeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TestShapeBlock extends Block {
    private static final DirectionVoxelShapesBundle SHAPE = OEVoxelShapeUtils.makeAllDirection(OEVoxelShapeUtils.getShapeFromResource(new ResourceLocation("test", "test1"), TestShapeBlock.class));

    public TestShapeBlock(Properties properties) {
        super(properties);
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE.getShape(Direction.NORTH);
    }
}
