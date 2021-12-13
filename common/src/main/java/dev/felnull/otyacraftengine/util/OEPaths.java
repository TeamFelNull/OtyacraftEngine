package dev.felnull.otyacraftengine.util;

import dev.felnull.otyacraftengine.OtyacraftEngine;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelResource;

import java.nio.file.Path;
import java.nio.file.Paths;

public class OEPaths {
    public static Path getClientOEFolderPath() {
        return Paths.get(OtyacraftEngine.MODID);
    }

    public static Path getWorldSaveDataPath(MinecraftServer server) {
        return server.getWorldPath(LevelResource.ROOT);
    }
}
