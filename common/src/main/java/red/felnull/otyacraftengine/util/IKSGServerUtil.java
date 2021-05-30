package red.felnull.otyacraftengine.util;

import dev.architectury.utils.GameInstance;
import net.minecraft.server.MinecraftServer;

public class IKSGServerUtil {
    public static MinecraftServer getMinecraftServer() {
        return GameInstance.getServer();
    }
}
