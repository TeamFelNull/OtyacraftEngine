package red.felnull.otyacraftengine.util;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

import java.util.List;
import java.util.UUID;

public class ServerHelper {
    public static MinecraftServer getMinecraftServer() {
        return LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);
    }

    public static List<ServerPlayerEntity> getOnlinePlayers() {
        return getMinecraftServer().getPlayerList().getPlayers();
    }

    public static boolean isOnlinePlayer(String uuid) {
        List<ServerPlayerEntity> playes = getOnlinePlayers();
        return playes.stream().anyMatch(n -> PlayerHelper.getUUID(n).equals(uuid));
    }


}
