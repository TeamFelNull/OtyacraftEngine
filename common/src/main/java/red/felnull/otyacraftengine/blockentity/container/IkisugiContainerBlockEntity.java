package red.felnull.otyacraftengine.blockentity.container;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import red.felnull.otyacraftengine.blockentity.IIkisugibleBlockEntity;

public abstract class IkisugiContainerBlockEntity extends BaseContainerBlockEntity implements IIkisugibleBlockEntity {

    protected IkisugiContainerBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    @Override
    public BlockEntity getBlockEntity() {
        return this;
    }

    public CompoundTag saveToTag(CompoundTag tag) {
        return tag;
    }

    abstract public boolean isAllEmpty();

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            syncble();
        }
    }

    @Override
    public boolean tickble() {
        return true;
    }

    public void setBlockStated(BlockState state) {
        this.level.setBlockAndUpdate(getBlockPos(), state);
    }
}
