package red.felnull.otyacraftengine.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntityType;

public abstract class IkisugiLockableTileEntity extends LockableTileEntity implements IClientSyncbleTileEntity, IInstructionTileEntity {
    protected IkisugiLockableTileEntity(TileEntityType<?> typeIn) {
        super(typeIn);
    }

    @Override
    public void func_230337_a_(BlockState state, CompoundNBT tag) {
        this.readByIKSG(state, tag);
    }

    public void readByIKSG(BlockState state, CompoundNBT tag) {
        super.func_230337_a_(state, tag);
    }

    public void setBlockState(BlockState state) {
        this.getWorld().setBlockState(getPos(), state);
    }

}
