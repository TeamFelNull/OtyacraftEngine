package red.felnull.otyacraftengine.util;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

import java.util.List;

public class IKSGServerUtil {
    /**
     * Get the server
     *
     * @return Minecraft Server
     */
    public static MinecraftServer getMinecraftServer() {
        return LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);
    }

    /**
     * Get the player list
     *
     * @return Server Player List
     */
    public static List<ServerPlayerEntity> getOnlinePlayers() {
        return getMinecraftServer().getPlayerList().getPlayers();
    }

    /**
     * Check if the player is online
     *
     * @param uuid PlayerUUID
     * @return Boolean value of the result
     */
    public static boolean isOnlinePlayer(String uuid) {
        List<ServerPlayerEntity> playes = getOnlinePlayers();
        return playes.stream().anyMatch(n -> IKSGPlayerUtil.getUUID(n).equals(uuid));
    }


}
