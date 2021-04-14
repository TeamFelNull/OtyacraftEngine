package red.felnull.otyacraftengine.data;

import net.minecraft.nbt.CompoundTag;

import java.nio.file.Path;
import java.nio.file.Paths;

public class TestSaveData extends IkisugiSaveData {
    private int testIntValue;

    @Override
    public CompoundTag save(CompoundTag compoundTag) {
        compoundTag.putInt("TestInt", testIntValue);
        return compoundTag;
    }

    @Override
    public void load(CompoundTag compoundTag) {
        testIntValue = compoundTag.getInt("TestInt");
    }

    @Override
    public Path getSavePath() {
        return Paths.get("test.dat");
    }

    public int getTestIntValue() {
        return testIntValue;
    }
}
