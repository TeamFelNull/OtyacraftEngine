package red.felnull.otyacraftengine.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class IkisugiTileEntity extends TileEntity {
    public IkisugiTileEntity(TileEntityType<?> type) {
        super(type);

    }

    @Override
    public void func_230337_a_(BlockState state, CompoundNBT tag) {
        this.readByIKSG(state, tag);
    }

    public void readByIKSG(BlockState state, CompoundNBT tag) {
        super.func_230337_a_(state, tag);
    }
}
