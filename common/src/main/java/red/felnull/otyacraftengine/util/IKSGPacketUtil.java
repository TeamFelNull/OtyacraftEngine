package red.felnull.otyacraftengine.util;

import net.minecraft.server.level.ServerPlayer;
import red.felnull.otyacraftengine.impl.OEPacketExpectPlatform;
import red.felnull.otyacraftengine.packet.IPacketMessage;
import red.felnull.otyacraftengine.packet.IPacketMessageClientHandler;
import red.felnull.otyacraftengine.packet.IPacketMessageServerHandler;

public class IKSGPacketUtil {
    public static <MSG extends IPacketMessage> void registerSendToClientPacket(Class<MSG> message, IPacketMessageClientHandler<MSG> handler) {
        OEPacketExpectPlatform.registerSendToClientPacket(message, IPacketMessage::encode, n -> {
            try {
                MSG pm = message.newInstance();
                pm.decode(n);
                return pm;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }, handler);
    }

    public static <MSG extends IPacketMessage> void registerSendToServerPacket(Class<MSG> message, IPacketMessageServerHandler<MSG> handler) {
        OEPacketExpectPlatform.registerSendToServerPacket(message, IPacketMessage::encode, n -> {
            try {
                MSG pm = message.newInstance();
                pm.decode(n);
                return pm;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }, handler);
    }

    public static <MSG extends IPacketMessage> void sendToClientPacket(ServerPlayer player, MSG message) {
        OEPacketExpectPlatform.sendToClientPacket(player, message);
    }

    public static <MSG extends IPacketMessage> void sendToServerPacket(MSG message) {
        OEPacketExpectPlatform.sendToServerPacket(message);
    }
}
