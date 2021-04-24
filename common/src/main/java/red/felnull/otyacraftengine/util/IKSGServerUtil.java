package red.felnull.otyacraftengine.util;

import me.shedaniel.architectury.utils.GameInstance;
import net.minecraft.server.MinecraftServer;

public class IKSGServerUtil {
    public static MinecraftServer getMinecraftServer() {
        return GameInstance.getServer();
    }
}
