package red.felnull.otyacraftengine.data.save;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;

import java.nio.file.Path;

public abstract class IkisugiSaveData extends SavedData {
    public abstract void load(CompoundTag compoundTag);

    public Path getSavePath() {
        return null;
    }

    public abstract void clear();
}
