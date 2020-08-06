package red.felnull.otyacraftengine.util;

import net.minecraft.world.storage.FolderName;

import java.nio.file.Path;

public class IKSGPathUtil {
    public static Path getWorldSaveDataPath() {
        return IKSGServerUtil.getMinecraftServer().func_240776_a_(new FolderName("dummy")).getParent();
    }

}
