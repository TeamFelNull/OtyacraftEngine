package red.felnull.otyacraftengine.packet;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.client.handler.ClientTileEntitySyncMessageHandler;
import red.felnull.otyacraftengine.client.handler.ServerToResponseMessageHandler;
import red.felnull.otyacraftengine.handler.ClientToResponseMessageHandler;

public class PacketHandler {
    public static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(OtyacraftEngine.MODID, OtyacraftEngine.MODID + "_channel")).clientAcceptedVersions(a -> true).serverAcceptedVersions(a -> true).networkProtocolVersion(() -> PROTOCOL_VERSION).simpleChannel();
    private static int integer = -1;

    private static int next() {
        integer++;
        return integer;
    }

    public static void init() {
        //クライアントタイルエンティティ同期
        INSTANCE.registerMessage(next(), ClientTileEntitySyncMessage.class, ClientTileEntitySyncMessage::encodeMessege, ClientTileEntitySyncMessage::decodeMessege, ClientTileEntitySyncMessageHandler::reversiveMessage);
        //クライアントからの応答メッセージ
        INSTANCE.registerMessage(next(), ClientToResponseMessage.class, ClientToResponseMessage::encodeMessege, ClientToResponseMessage::decodeMessege, ClientToResponseMessageHandler::reversiveMessage);
        //サーバーからの応答メッセージ
        INSTANCE.registerMessage(next(), ServerToResponseMessage.class, ServerToResponseMessage::encodeMessege, ServerToResponseMessage::decodeMessege, ServerToResponseMessageHandler::reversiveMessage);
    }
}
