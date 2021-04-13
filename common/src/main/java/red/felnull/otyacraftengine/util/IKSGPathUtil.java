package red.felnull.otyacraftengine.util;

import net.minecraft.world.level.storage.LevelResource;
import red.felnull.otyacraftengine.OtyacraftEngine;

import java.nio.file.Path;
import java.nio.file.Paths;

public class IKSGPathUtil {
    public static Path getWorldSaveDataPath() {
        return IKSGServerUtil.getMinecraftServer().getWorldPath(LevelResource.ROOT);
    }

    public static Path getOtyacraftEngineDataPath() {
        return Paths.get(OtyacraftEngine.MODID);
    }

    public static Path getOtyacraftEngineWorldDataPath() {
        return getMODWorldDataPath(OtyacraftEngine.MODID);
    }

    public static Path getMODWorldDataPath(String modid) {
        return getWorldSaveDataPath().resolve(modid);
    }
}
