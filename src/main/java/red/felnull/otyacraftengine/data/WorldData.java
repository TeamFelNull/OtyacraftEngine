package red.felnull.otyacraftengine.data;

import net.minecraft.nbt.CompoundNBT;

import java.nio.file.Path;

public abstract class WorldData {
    public abstract Path getSavedFolderPath();

    public abstract CompoundNBT getInitialNBT(CompoundNBT tag);
}
