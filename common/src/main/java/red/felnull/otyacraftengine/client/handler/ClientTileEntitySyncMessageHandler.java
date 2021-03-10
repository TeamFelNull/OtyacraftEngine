package red.felnull.otyacraftengine.client.handler;

import net.minecraft.client.multiplayer.ClientPacketListener;
import red.felnull.otyacraftengine.packet.ClientTileEntitySyncMessage;
import red.felnull.otyacraftengine.packet.IPacketMessageClientHandler;

public class ClientTileEntitySyncMessageHandler implements IPacketMessageClientHandler<ClientTileEntitySyncMessage> {
    @Override
    public boolean reversiveMessage(ClientTileEntitySyncMessage message, ClientPacketListener handler) {
        return true;
    }
}
