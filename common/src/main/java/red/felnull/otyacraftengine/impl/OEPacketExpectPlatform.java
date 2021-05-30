package red.felnull.otyacraftengine.impl;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import red.felnull.otyacraftengine.packet.IPacketMessage;
import red.felnull.otyacraftengine.packet.IPacketMessageClientHandler;
import red.felnull.otyacraftengine.packet.IPacketMessageServerHandler;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class OEPacketExpectPlatform {
    @ExpectPlatform
    public static <MSG extends IPacketMessage> void registerSendToServerPacket(Class<MSG> messageType, BiConsumer<MSG, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, MSG> decoder, IPacketMessageServerHandler<MSG> messageHandler) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <MSG extends IPacketMessage> void registerSendToClientPacket(Class<MSG> messageType, BiConsumer<MSG, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, MSG> decoder, IPacketMessageClientHandler<MSG> messageHandler) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <MSG extends IPacketMessage> void sendToClientPacket(ServerPlayer player, MSG message) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <MSG extends IPacketMessage> void sendToServerPacket(MSG message) {
        throw new AssertionError();
    }
}
