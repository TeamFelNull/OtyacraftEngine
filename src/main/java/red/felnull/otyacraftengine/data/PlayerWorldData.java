package red.felnull.otyacraftengine.data;

import net.minecraft.nbt.CompoundNBT;

import java.nio.file.Path;

public abstract class PlayerWorldData {
    public abstract boolean isClientSincble();

    public abstract Path getSavedFolderPath();

    public abstract CompoundNBT getDefaltNBT(CompoundNBT tag);
}
