package dev.felnull.otyacraftengine.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;

import java.nio.file.Path;

public abstract class OEBaseSaveData extends SavedData {
    public abstract void load(CompoundTag tag);

    public Path getSavePath() {
        return null;
    }

    public abstract void clear();
}
