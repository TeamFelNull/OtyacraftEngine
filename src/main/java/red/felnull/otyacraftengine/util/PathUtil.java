package red.felnull.otyacraftengine.util;

import net.minecraft.server.MinecraftServer;

import java.nio.file.Path;

public class PathUtil {
    public static Path getWorldSaveDataPath(MinecraftServer ms) {
        return ms.getActiveAnvilConverter().getFile(ms.getFolderName(), "test").getParentFile().toPath();
    }
}
