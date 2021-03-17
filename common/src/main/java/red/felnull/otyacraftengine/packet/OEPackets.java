package red.felnull.otyacraftengine.packet;

import red.felnull.otyacraftengine.api.OtyacraftEngineAPI;
import red.felnull.otyacraftengine.client.handler.ClientTestMessageHandler;
import red.felnull.otyacraftengine.client.handler.ClientTileEntitySyncMessageHandler;
import red.felnull.otyacraftengine.handler.ServerTestMessageHandler;
import red.felnull.otyacraftengine.util.IKSGPacketUtil;

public class OEPackets {
    public static void init() {
        IKSGPacketUtil.registerSendToClientPacket(ClientTileEntitySyncMessage.class, new ClientTileEntitySyncMessageHandler());

        OtyacraftEngineAPI api = OtyacraftEngineAPI.getInstance();
        if (api.isTestMode()) {
            IKSGPacketUtil.registerSendToClientPacket(ClientTestMessage.class, new ClientTestMessageHandler());
            IKSGPacketUtil.registerSendToServerPacket(ServerTestMessage.class, new ServerTestMessageHandler());
        }
    }
}