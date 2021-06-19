package red.felnull.otyacraftengine.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.chunk.LevelChunk;
import red.felnull.otyacraftengine.packet.SimpleSendToClientMessage;
import red.felnull.otyacraftengine.packet.SimpleSendToServerMessage;
import red.felnull.otyacraftengine.util.IKSGPacketUtil;

import java.util.UUID;

public class SimpleMessageSender {
    public static void sendToServer(ResourceLocation location, int id, CompoundTag data) {
        IKSGPacketUtil.sendToServerPacket(new SimpleSendToServerMessage(location, id, data));
    }

    public static void sendToClient(MinecraftServer server, UUID playerID, ResourceLocation location, int id, CompoundTag data) {
        IKSGPacketUtil.sendToClientPacket(server, playerID, new SimpleSendToClientMessage(location, id, data));
    }

    public static void sendToClient(UUID playerID, ResourceLocation location, int id, CompoundTag data) {
        IKSGPacketUtil.sendToClientPacket(playerID, new SimpleSendToClientMessage(location, id, data));
    }

    public static void sendToClient(ServerPlayer player, ResourceLocation location, int id, CompoundTag data) {
        IKSGPacketUtil.sendToClientPacket(player, new SimpleSendToClientMessage(location, id, data));
    }

    public static void sendToClient(LevelChunk chunk, ResourceLocation location, int id, CompoundTag data) {
        IKSGPacketUtil.sendToClientPacket(chunk, new SimpleSendToClientMessage(location, id, data));
    }

    public static void sendToClient(ResourceLocation location, int id, CompoundTag data) {
        IKSGPacketUtil.sendToClientPacket(new SimpleSendToClientMessage(location, id, data));
    }

    public static void sendToClient(MinecraftServer server, ResourceLocation location, int id, CompoundTag data) {
        IKSGPacketUtil.sendToClientPacket(server, new SimpleSendToClientMessage(location, id, data));
    }
}
