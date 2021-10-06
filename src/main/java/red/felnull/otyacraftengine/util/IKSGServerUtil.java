package red.felnull.otyacraftengine.util;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;
import red.felnull.otyacraftengine.api.registries.OERegistries;
import red.felnull.otyacraftengine.data.WorldDataManager;

import java.util.List;
import java.util.UUID;

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
    @Deprecated
    public static boolean isOnlinePlayer(String uuid) {
        return getMinecraftServer().getPlayerList().getPlayerByUUID(UUID.fromString(uuid)) != null;
    }

    /**
     * @return World UUID
     */
    public static UUID getWorldUUID() {
        String wuuid = WorldDataManager.instance().getWorldData(OERegistries.WORDDEFINITIVEDATA).getString("worduuid");

        if (wuuid.isEmpty())
            return null;

        return UUID.fromString(wuuid);
    }


}
