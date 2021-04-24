package red.felnull.otyacraftengine.impl.fabric;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import red.felnull.otyacraftengine.fabric.packet.PacketHandler;
import red.felnull.otyacraftengine.packet.IPacketMessage;
import red.felnull.otyacraftengine.packet.IPacketMessageClientHandler;
import red.felnull.otyacraftengine.packet.IPacketMessageServerHandler;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class OEPacketExpectPlatformImpl {
    public static <MSG extends IPacketMessage> void registerSendToServerPacket(Class<MSG> messageType, BiConsumer<MSG, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, MSG> decoder, IPacketMessageServerHandler<MSG> messageHandler) {
        PacketHandler.registerSendToServerPacket(messageType, encoder, decoder, messageHandler);
    }

    public static <MSG extends IPacketMessage> void registerSendToClientPacket(Class<MSG> messageType, BiConsumer<MSG, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, MSG> decoder, IPacketMessageClientHandler<MSG> messageHandler) {
        PacketHandler.registerSendToClientPacket(messageType, encoder, decoder, messageHandler);
    }

    public static <MSG extends IPacketMessage> void sendToClientPacket(ServerPlayer player, MSG message) {
        PacketHandler.sendToClientPacket(player, message);
    }

    public static <MSG extends IPacketMessage> void sendToServerPacket(MSG message) {
        PacketHandler.sendToServerPacket(message);
    }
}
