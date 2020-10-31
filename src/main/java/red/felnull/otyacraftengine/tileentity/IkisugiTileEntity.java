package red.felnull.otyacraftengine.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public abstract class IkisugiTileEntity extends TileEntity implements IClientSyncbleTileEntity, IInstructionTileEntity {
    public IkisugiTileEntity(TileEntityType<?> type) {
        super(type);

    }

    @Override
    public void read(BlockState state, CompoundNBT tag) {
        this.readByIKSG(state, tag);
    }

    public void readByIKSG(BlockState state, CompoundNBT tag) {
        super.read(state, tag);
    }

    public void setBlockState(BlockState state) {
        this.getWorld().setBlockState(getPos(), state);
    }

    @Override
    public CompoundNBT instructionFromClient(ServerPlayerEntity player, String name, CompoundNBT data) {
        return null;
    }

    @Override
    public boolean canInteractWith(ServerPlayerEntity player, String name, CompoundNBT data) {
        return true;
    }
}
