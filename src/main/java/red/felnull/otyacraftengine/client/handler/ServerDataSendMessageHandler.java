package red.felnull.otyacraftengine.client.handler;

import net.minecraftforge.fml.network.NetworkEvent;
import red.felnull.otyacraftengine.packet.ClientDataSendMessage;
import red.felnull.otyacraftengine.packet.ServerDataSendMessage;

import java.util.function.Supplier;

public class ServerDataSendMessageHandler {
    public static void reversiveMessage(ServerDataSendMessage message, Supplier<NetworkEvent.Context> ctx) {

    }
}
