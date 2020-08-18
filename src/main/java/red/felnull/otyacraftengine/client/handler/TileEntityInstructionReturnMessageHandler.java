package red.felnull.otyacraftengine.client.handler;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.network.NetworkEvent;
import red.felnull.otyacraftengine.api.event.client.ReturnInstructionEvent;
import red.felnull.otyacraftengine.packet.TileEntityInstructionReturnMessage;

import java.util.function.Supplier;

public class TileEntityInstructionReturnMessageHandler {
    public static void reversiveMessage(TileEntityInstructionReturnMessage message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().setPacketHandled(true);
        MinecraftForge.EVENT_BUS.post(new ReturnInstructionEvent(message.pos, message.name, message.data));
    }
}
