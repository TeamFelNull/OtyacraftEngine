package red.felnull.otyacraftengine.data;

import net.minecraft.nbt.CompoundNBT;
import red.felnull.otyacraftengine.OtyacraftEngine;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class OEDefinitiveWorldData extends WorldData {
    @Override
    public Path getSavedFolderPath() {
        return Paths.get(OtyacraftEngine.MODID + ".dat");
    }

    @Override
    public CompoundNBT getInitialNBT(CompoundNBT tag) {
        tag.putString("worduuid", UUID.randomUUID().toString());
        return tag;
    }
}
