package red.felnull.otyacraftengine.data;

import net.minecraft.nbt.CompoundNBT;

import java.nio.file.Path;
import java.nio.file.Paths;

public class TestWorldData extends WorldData {
    @Override
    public Path getSavedFolderPath() {
        return Paths.get("otyacraftengine\\testdata.dat");
    }

    @Override
    public CompoundNBT getInitialNBT(CompoundNBT tag) {
        tag.putInt("test", 810);
        return tag;
    }
}
