package red.felnull.otyacraftengine.client.handler;

import net.minecraft.client.multiplayer.ClientPacketListener;
import red.felnull.otyacraftengine.api.OEHandlerBus;
import red.felnull.otyacraftengine.api.event.SimpleMessageEvent;
import red.felnull.otyacraftengine.packet.IPacketMessageClientHandler;
import red.felnull.otyacraftengine.packet.SimpleSendToClientMessage;

public class SimpleSendToClientMessageHandler implements IPacketMessageClientHandler<SimpleSendToClientMessage> {
    @Override
    public boolean reversiveMessage(SimpleSendToClientMessage message, ClientPacketListener handler) {
        return OEHandlerBus.post(new SimpleMessageEvent.Client(message.location, message.id, message.data));
    }
}
