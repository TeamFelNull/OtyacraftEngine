package net.examplemod.server.saveddata;

import dev.felnull.otyacraftengine.server.level.saveddata.OEBaseSavedData;
import dev.felnull.otyacraftengine.server.util.OESaveDataUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;

public class TestSavedData extends OEBaseSavedData {
    private String name = "";

    public static TestSavedData get(MinecraftServer server) {
        return OESaveDataUtils.getSaveData(server, "test_savedata", TestSavedData::new);
    }

    @Override
    public void load(CompoundTag tag) {
        this.name = tag.getString("name");
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.putString("name", name);
        return tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        setDirty();
    }
}
