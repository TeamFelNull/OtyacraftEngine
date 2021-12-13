package dev.felnull.otyacraftengine.data;

import net.minecraft.nbt.CompoundTag;

public class TestSaveData extends OEBaseSaveData {
    private String test;

    public TestSaveData() {
        setDirty();
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag) {
        compoundTag.putString("Test", test);
        return compoundTag;
    }

    @Override
    public void load(CompoundTag compoundTag) {
        test = compoundTag.getString("Test");
    }

    @Override
    public void clear() {
        test = "";
        setDirty();
    }

    public void setTest(String test) {
        this.test = test;
        setDirty();
    }

    public String getTest() {
        return test;
    }
}
