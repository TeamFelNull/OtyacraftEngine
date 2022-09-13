package dev.felnull.otyacraftenginetest;

import dev.felnull.otyacraftengine.server.level.TagSerializable;
import net.minecraft.nbt.CompoundTag;

import java.util.Objects;

public class TestTagSerializable implements TagSerializable {
    private String name = "";
    private int val;

    public TestTagSerializable() {
    }

    public TestTagSerializable(String name, int val) {
        this.name = name;
        this.val = val;
    }

    @Override
    public void save(CompoundTag tag) {
        tag.putString("name", name);
        tag.putInt("val", val);
    }

    @Override
    public void load(CompoundTag tag) {
        name = tag.getString("name");
        val = tag.getInt("val");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestTagSerializable that = (TestTagSerializable) o;
        return val == that.val && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, val);
    }
}
