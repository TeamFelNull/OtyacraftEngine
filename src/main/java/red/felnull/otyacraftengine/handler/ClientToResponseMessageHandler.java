package red.felnull.otyacraftengine.handler;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.network.NetworkEvent;
import red.felnull.otyacraftengine.api.event.common.ResponseEvent;
import red.felnull.otyacraftengine.packet.ClientToResponseMessage;

import java.util.function.Supplier;

public class ClientToResponseMessageHandler {
    public static void reversiveMessage(ClientToResponseMessage message, Supplier<NetworkEvent.Context> ctx) {
        MinecraftForge.EVENT_BUS.post(new ResponseEvent.Client(ctx.get().getSender(), message.location, message.id, message.message, message.data));
    }
}
