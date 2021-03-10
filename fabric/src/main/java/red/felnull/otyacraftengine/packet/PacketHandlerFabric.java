package red.felnull.otyacraftengine.packet;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import red.felnull.otyacraftengine.OtyacraftEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class PacketHandlerFabric {
    private static final Map<Class<? extends IPacketMessage>, ResourceLocation> LOCATIONS = new HashMap<>();
    private static int svnumber;
    private static int clnumber;

    public static <MSG extends IPacketMessage> void registerSendToServerPacket(Class<MSG> messageType, BiConsumer<MSG, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, MSG> decoder, IPacketMessageServerHandler<MSG> messageHandler) {
        ResourceLocation location = new ResourceLocation(OtyacraftEngine.MODID, "server_" + svnumber++ + "_packet");
        ServerPlayNetworking.registerGlobalReceiver(location, (server, player, handler, buf, responseSender) -> messageHandler.reversiveMessage(decoder.apply(buf), player, handler));
    }

    public static <MSG extends IPacketMessage> void registerSendToClientPacket(Class<MSG> messageType, BiConsumer<MSG, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, MSG> decoder, IPacketMessageClientHandler<MSG> messageHandler) {
        ResourceLocation location = new ResourceLocation(OtyacraftEngine.MODID, "client_" + clnumber++ + "_packet");
        ClientPlayNetworking.registerGlobalReceiver(location, (client, handler, buf, responseSender) -> messageHandler.reversiveMessage(decoder.apply(buf), handler));
    }

    public static <MSG extends IPacketMessage> void sendToClientPacket(ServerPlayer player, MSG message) {
        Class<? extends IPacketMessage> msgclass = message.getClass();
        FriendlyByteBuf buf = PacketByteBufs.create();
        message.encode(buf);
        ServerPlayNetworking.send(player, LOCATIONS.get(msgclass), buf);
    }

    public static <MSG extends IPacketMessage> void sendToServerPacket(MSG message) {
        Class<? extends IPacketMessage> msgclass = message.getClass();
        FriendlyByteBuf buf = PacketByteBufs.create();
        message.encode(buf);
        ClientPlayNetworking.send(LOCATIONS.get(msgclass), buf);
    }
}
