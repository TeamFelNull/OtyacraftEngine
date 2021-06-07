package red.felnull.otyacraftengine.handler;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import red.felnull.otyacraftengine.api.event.OEEventBus;
import red.felnull.otyacraftengine.api.event.SimpleMessageEvent;
import red.felnull.otyacraftengine.packet.IPacketMessageServerHandler;
import red.felnull.otyacraftengine.packet.SimpleSendToServerMessage;

public class SimpleSendToServerMessageHandler implements IPacketMessageServerHandler<SimpleSendToServerMessage> {
    @Override
    public boolean reversiveMessage(SimpleSendToServerMessage message, ServerPlayer player, ServerGamePacketListenerImpl handler) {
        return OEEventBus.post(new SimpleMessageEvent.Server(player, message.location, message.id, message.data));
    }
}
