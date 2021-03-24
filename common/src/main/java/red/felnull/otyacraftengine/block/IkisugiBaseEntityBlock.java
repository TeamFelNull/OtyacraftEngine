package red.felnull.otyacraftengine.block;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import red.felnull.otyacraftengine.blockentity.IIkisugibleBlockEntity;
import red.felnull.otyacraftengine.blockentity.ITickbleBlockEntity;
import red.felnull.otyacraftengine.blockentity.TestBlockEntity;

public abstract class IkisugiBaseEntityBlock extends BaseEntityBlock implements IIkisugibleBlock {
    protected IkisugiBaseEntityBlock(Properties properties) {
        super(properties);
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Override
    public ResourceLocation getRegistryName() {
        return Registry.BLOCK.getKey(this);
    }

    public BlockEntityType<? extends IIkisugibleBlockEntity> getTickerBlockEntityType() {
        return null;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        if (blockEntityType == getTickerBlockEntityType()) {
            return (level1, blockPos, blockState1, blockEntity) -> {
                if (blockEntity instanceof ITickbleBlockEntity && ((TestBlockEntity) blockEntity).tickble()) {
                    ((TestBlockEntity) blockEntity).tick();
                }
            };
        }
        return null;
    }
}
