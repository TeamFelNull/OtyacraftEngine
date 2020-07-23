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
        if (!tag.contains("test"))
            tag.putInt("test", 0);
        return tag;
    }
}
