package red.felnull.otyacraftengine.util;

import net.minecraft.world.storage.FolderName;
import red.felnull.otyacraftengine.OtyacraftEngine;

import java.nio.file.Path;
import java.nio.file.Paths;

public class IKSGPathUtil {
    public static Path getWorldSaveDataPath() {
        return IKSGServerUtil.getMinecraftServer().func_240776_a_(new FolderName("dummy")).getParent();
    }

    public static Path getOEDataPath() {
        return Paths.get(OtyacraftEngine.MODID);
    }

    public static Path getOEReceiveTexturesPath() {
        return getOEDataPath().resolve("receivetextures");
    }

    public static Path getOEURLImageTexturesPath() {
        return getOEDataPath().resolve("urlimagetextures");
    }
}
