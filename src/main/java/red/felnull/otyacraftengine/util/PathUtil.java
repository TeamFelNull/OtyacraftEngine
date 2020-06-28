package red.felnull.otyacraftengine.util;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.FolderName;

import java.nio.file.Path;

public class PathUtil {
    public static Path getWorldSaveDataPath(MinecraftServer ms) {
        return ms.func_240776_a_(new FolderName("test")).getParent();
    }
}
