package red.felnull.otyacraftengine.data;

import net.minecraft.nbt.CompoundNBT;

public interface INBTReadWriter {
    void read(CompoundNBT tag);

    CompoundNBT write(CompoundNBT tag);
}
