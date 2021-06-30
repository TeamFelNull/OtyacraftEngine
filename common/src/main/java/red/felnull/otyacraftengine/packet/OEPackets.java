package red.felnull.otyacraftengine.packet;

import red.felnull.otyacraftengine.api.OtyacraftEngineAPI;
import red.felnull.otyacraftengine.client.handler.BlockEntityInstructionReturnMessageHandler;
import red.felnull.otyacraftengine.client.handler.ClientBlockEntitySyncMessageHandler;
import red.felnull.otyacraftengine.client.handler.ClientTestMessageHandler;
import red.felnull.otyacraftengine.client.handler.SimpleSendToClientMessageHandler;
import red.felnull.otyacraftengine.handler.BlockEntityInstructionMessageHandler;
import red.felnull.otyacraftengine.handler.ServerTestMessageHandler;
import red.felnull.otyacraftengine.handler.SimpleSendToServerMessageHandler;
import red.felnull.otyacraftengine.handler.WorldShareUploadMessageHandler;
import red.felnull.otyacraftengine.util.IKSGPacketUtil;

public class OEPackets {
    public static void init() {
        IKSGPacketUtil.registerSendToClientPacket(ClientTileEntitySyncMessage.class, ClientTileEntitySyncMessage::new, new ClientBlockEntitySyncMessageHandler());
        IKSGPacketUtil.registerSendToClientPacket(BlockEntityInstructionReturnMessage.class, BlockEntityInstructionReturnMessage::new, new BlockEntityInstructionReturnMessageHandler());
        IKSGPacketUtil.registerSendToClientPacket(SimpleSendToClientMessage.class, SimpleSendToClientMessage::new, new SimpleSendToClientMessageHandler());

        IKSGPacketUtil.registerSendToServerPacket(BlockEntityInstructionMessage.class, BlockEntityInstructionMessage::new, new BlockEntityInstructionMessageHandler());
        IKSGPacketUtil.registerSendToServerPacket(WorldShareUploadMessage.class, WorldShareUploadMessage::new, new WorldShareUploadMessageHandler());
        IKSGPacketUtil.registerSendToServerPacket(SimpleSendToServerMessage.class, SimpleSendToServerMessage::new, new SimpleSendToServerMessageHandler());

        OtyacraftEngineAPI api = OtyacraftEngineAPI.getInstance();

        if (api.isTestMode()) {
            IKSGPacketUtil.registerSendToClientPacket(ClientTestMessage.class, ClientTestMessage::new, new ClientTestMessageHandler());
            IKSGPacketUtil.registerSendToServerPacket(ServerTestMessage.class, ServerTestMessage::new, new ServerTestMessageHandler());
        }
    }
}
