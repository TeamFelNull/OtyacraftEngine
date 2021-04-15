package red.felnull.otyacraftengine.util;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelResource;
import red.felnull.otyacraftengine.OtyacraftEngine;

import java.nio.file.Path;
import java.nio.file.Paths;

public class IKSGPathUtil {
    public static Path getWorldSaveDataPath(MinecraftServer server) {
        return server.getWorldPath(LevelResource.ROOT);
    }

    public static Path getWorldSaveDataPath() {
        return getWorldSaveDataPath(IKSGServerUtil.getMinecraftServer());
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
