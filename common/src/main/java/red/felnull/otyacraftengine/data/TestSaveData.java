package red.felnull.otyacraftengine.data;

import net.minecraft.nbt.CompoundTag;

import java.nio.file.Path;
import java.nio.file.Paths;

public class TestSaveData extends IkisugiSaveData {
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
    public Path getSavePath() {
        return Paths.get("test.dat");
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
