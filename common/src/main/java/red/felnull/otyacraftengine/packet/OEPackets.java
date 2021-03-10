package red.felnull.otyacraftengine.packet;

import red.felnull.otyacraftengine.client.handler.ClientTileEntitySyncMessageHandler;
import red.felnull.otyacraftengine.util.IKSGPacketUtil;

public class OEPackets {
    public static void init() {
        IKSGPacketUtil.registerSendToClientPacket(ClientTileEntitySyncMessage.class, new ClientTileEntitySyncMessageHandler());
    }
}
