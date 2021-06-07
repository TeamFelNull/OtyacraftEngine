package red.felnull.otyacraftengine.handler;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import red.felnull.otyacraftengine.packet.IPacketMessageServerHandler;
import red.felnull.otyacraftengine.packet.WorldShareUploadMessage;

public class WorldShareUploadMessageHandler implements IPacketMessageServerHandler<WorldShareUploadMessage> {
    @Override
    public boolean reversiveMessage(WorldShareUploadMessage message, ServerPlayer player, ServerGamePacketListenerImpl handler) {


        return true;
    }
}
