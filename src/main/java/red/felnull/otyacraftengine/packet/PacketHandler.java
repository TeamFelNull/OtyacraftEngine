package red.felnull.otyacraftengine.packet;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.client.handler.*;
import red.felnull.otyacraftengine.handler.ClientDataSendMessageHandler;
import red.felnull.otyacraftengine.handler.ClientToResponseMessageHandler;
import red.felnull.otyacraftengine.handler.TileEntityInstructionMessageHandler;

public class PacketHandler {
    public static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(OtyacraftEngine.MODID, OtyacraftEngine.MODID + "_channel")).clientAcceptedVersions(a -> true).serverAcceptedVersions(a -> true).networkProtocolVersion(() -> PROTOCOL_VERSION).simpleChannel();
    private static int integer = -1;

    private static int next() {
        integer++;
        return integer;
    }

    public static <MSG> void sendPacket(ServerPlayerEntity playerEntity, MSG message) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> playerEntity), message);
    }

    public static void init() {
        //クライアントタイルエンティティ同期
        INSTANCE.registerMessage(next(), ClientTileEntitySyncMessage.class, ClientTileEntitySyncMessage::encodeMessege, ClientTileEntitySyncMessage::decodeMessege, ClientTileEntitySyncMessageHandler::reversiveMessage);
        //クライアントからの応答メッセージ
        INSTANCE.registerMessage(next(), ClientToResponseMessage.class, ClientToResponseMessage::encodeMessege, ClientToResponseMessage::decodeMessege, ClientToResponseMessageHandler::reversiveMessage);
        //サーバーからの応答メッセージ
        INSTANCE.registerMessage(next(), ServerToResponseMessage.class, ServerToResponseMessage::encodeMessege, ServerToResponseMessage::decodeMessege, ServerToResponseMessageHandler::reversiveMessage);
        //クライアントプレイヤーデーター同期
        INSTANCE.registerMessage(next(), ClientPlayerDataSyncMessage.class, ClientPlayerDataSyncMessage::encodeMessege, ClientPlayerDataSyncMessage::decodeMessege, ClientPlayerDataSyncMessageHandler::reversiveMessage);
        //クライアントからサーバーへデータ送信
        INSTANCE.registerMessage(next(), ClientDataSendMessage.class, ClientDataSendMessage::encodeMessege, ClientDataSendMessage::decodeMessege, ClientDataSendMessageHandler::reversiveMessage);
        //サーバーからクライアントへデータ送信
        INSTANCE.registerMessage(next(), ServerDataSendMessage.class, ServerDataSendMessage::encodeMessege, ServerDataSendMessage::decodeMessege, ServerDataSendMessageHandler::reversiveMessage);
        //タイルエンティティへクライアントからの指示
        INSTANCE.registerMessage(next(), TileEntityInstructionMessage.class, TileEntityInstructionMessage::encodeMessege, TileEntityInstructionMessage::decodeMessege, TileEntityInstructionMessageHandler::reversiveMessage);
        //タイルエンティティへクライアントからの指示の返し
        INSTANCE.registerMessage(next(), TileEntityInstructionReturnMessage.class, TileEntityInstructionReturnMessage::encodeMessege, TileEntityInstructionReturnMessage::decodeMessege, TileEntityInstructionReturnMessageHandler::reversiveMessage);
    }
}
