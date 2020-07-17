package red.felnull.otyacraftengine.data;

import net.minecraft.nbt.CompoundNBT;

import java.nio.file.Path;
import java.nio.file.Paths;

public class TestPlayerWorldData extends PlayerWorldData {
    @Override
    public boolean isClientSincble() {
        return true;
    }

    @Override
    public Path getSavedFolderPath() {
        return Paths.get("otyacraftengine\\testplayerdata");
    }

    @Override
    public CompoundNBT getDefaltNBT(CompoundNBT tag) {
        tag.putInt("test", 0);
        return tag;
    }
}
