package red.felnull.otyacraftengine.client.handler;

import net.minecraftforge.fml.network.NetworkEvent;
import red.felnull.otyacraftengine.data.WorldDataManager;
import red.felnull.otyacraftengine.packet.ClientPlayerDataSyncMessage;

import java.util.function.Supplier;

public class ClientPlayerDataSyncMessageHandler {
    public static void reversiveMessage(ClientPlayerDataSyncMessage message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().setPacketHandled(true);
        WorldDataManager.instance().CLIENT_PLAYER_DATA.put(message.location, message.tag);
    }
}
