package red.felnull.otyacraftengine.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;

import java.nio.file.Path;

public abstract class IkisugiSaveData extends SavedData {
    public abstract void load(CompoundTag compoundTag);

    public abstract Path getSavePath();
}
