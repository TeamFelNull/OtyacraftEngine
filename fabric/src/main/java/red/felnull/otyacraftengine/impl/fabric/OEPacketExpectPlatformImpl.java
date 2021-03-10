package red.felnull.otyacraftengine.impl.fabric;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import red.felnull.otyacraftengine.packet.IPacketMessage;
import red.felnull.otyacraftengine.packet.IPacketMessageClientHandler;
import red.felnull.otyacraftengine.packet.IPacketMessageServerHandler;
import red.felnull.otyacraftengine.packet.PacketHandlerFabric;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class OEPacketExpectPlatformImpl {
    public static <MSG extends IPacketMessage> void registerSendToServerPacket(Class<MSG> messageType, BiConsumer<MSG, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, MSG> decoder, IPacketMessageServerHandler<MSG> messageHandler) {
        PacketHandlerFabric.registerSendToServerPacket(messageType, encoder, decoder, messageHandler);
    }

    public static <MSG extends IPacketMessage> void registerSendToClientPacket(Class<MSG> messageType, BiConsumer<MSG, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, MSG> decoder, IPacketMessageClientHandler<MSG> messageHandler) {
        PacketHandlerFabric.registerSendToClientPacket(messageType, encoder, decoder, messageHandler);
    }

    public static <MSG extends IPacketMessage> void sendToClientPacket(ServerPlayer player, MSG message) {
        PacketHandlerFabric.sendToClientPacket(player, message);
    }

    public static <MSG extends IPacketMessage> void sendToServerPacket(MSG message) {
        PacketHandlerFabric.sendToServerPacket(message);
    }
}
