package red.felnull.otyacraftengine.util;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

public class ServerHelper {
    public static MinecraftServer getMinecraftServer() {
        return LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);
    }
}
