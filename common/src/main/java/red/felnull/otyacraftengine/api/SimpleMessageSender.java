package red.felnull.otyacraftengine.api;

import dev.architectury.utils.GameInstance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import red.felnull.otyacraftengine.packet.SimpleSendToClientMessage;
import red.felnull.otyacraftengine.packet.SimpleSendToServerMessage;
import red.felnull.otyacraftengine.util.IKSGPacketUtil;

import java.util.UUID;

public class SimpleMessageSender {
    public static void sendToServer(ResourceLocation location, int id, CompoundTag data) {
        IKSGPacketUtil.sendToServerPacket(new SimpleSendToServerMessage(location, id, data));
    }

    public static void sendToClient(UUID playerID, ResourceLocation location, int id, CompoundTag data) {
        if (GameInstance.getServer() != null) {
            ServerPlayer player = GameInstance.getServer().getPlayerList().getPlayer(playerID);
            sendToClient(player, location, id, data);
        }
    }

    public static void sendToClient(ServerPlayer player, ResourceLocation location, int id, CompoundTag data) {
        IKSGPacketUtil.sendToClientPacket(player, new SimpleSendToClientMessage(location, id, data));
    }
}
