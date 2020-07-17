package red.felnull.otyacraftengine.client.handler;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.network.NetworkEvent;
import red.felnull.otyacraftengine.api.event.ResponseEvent;
import red.felnull.otyacraftengine.packet.ServerToResponseMessage;

import java.util.function.Supplier;

public class ServerToResponseMessageHandler {
    public static void reversiveMessage(ServerToResponseMessage message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().setPacketHandled(true);
        MinecraftForge.EVENT_BUS.post(new ResponseEvent.Server(message.location, message.id, message.message, message.data));
    }
}
