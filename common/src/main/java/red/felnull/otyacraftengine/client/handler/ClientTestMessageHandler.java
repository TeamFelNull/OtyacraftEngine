package red.felnull.otyacraftengine.client.handler;

import net.minecraft.client.multiplayer.ClientPacketListener;
import red.felnull.otyacraftengine.packet.ClientTestMessage;
import red.felnull.otyacraftengine.packet.IPacketMessageClientHandler;

public class ClientTestMessageHandler implements IPacketMessageClientHandler<ClientTestMessage> {
    @Override
    public boolean reversiveMessage(ClientTestMessage message, ClientPacketListener handler) {
        System.out.println("test:" + message.name);
        return true;
    }
}
