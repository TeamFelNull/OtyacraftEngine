package dev.felnull.otyacraftengine.data;

import net.minecraft.nbt.CompoundTag;

public interface ITAGSerializable {
    CompoundTag save(CompoundTag tag);

    void load(CompoundTag tag);
}