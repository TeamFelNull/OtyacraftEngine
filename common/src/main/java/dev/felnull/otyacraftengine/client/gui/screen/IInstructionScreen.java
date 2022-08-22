package dev.felnull.otyacraftengine.client.gui.screen;

import net.minecraft.nbt.CompoundTag;

import java.util.UUID;

public interface IInstructionScreen {
    UUID getInstructionID();

    void instruction(String name, int num, CompoundTag data);

    void onInstructionReturn(String name, int num, CompoundTag data);
}