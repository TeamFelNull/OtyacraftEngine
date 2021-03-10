package red.felnull.otyacraftengine.handler;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import red.felnull.otyacraftengine.packet.IPacketMessageServerHandler;
import red.felnull.otyacraftengine.packet.ServerTestMessage;

public class ServerTestMessageHandler implements IPacketMessageServerHandler<ServerTestMessage> {
    @Override
    public boolean reversiveMessage(ServerTestMessage message, ServerPlayer player, ServerGamePacketListenerImpl handler) {
        System.out.println("teserver:" + message.name);
        return true;
    }
}
