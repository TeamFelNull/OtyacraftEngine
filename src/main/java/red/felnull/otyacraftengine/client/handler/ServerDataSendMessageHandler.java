package red.felnull.otyacraftengine.client.handler;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.api.ResponseSender;
import red.felnull.otyacraftengine.client.data.ClientDataReceiver;
import red.felnull.otyacraftengine.packet.ServerDataSendMessage;

import java.util.function.Supplier;

public class ServerDataSendMessageHandler {
    private static final ResourceLocation SERVER_RESPONSE = new ResourceLocation(OtyacraftEngine.MODID, "server_response");

    public static void reversiveMessage(ServerDataSendMessage message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().setPacketHandled(true);
        if (message.frist) {
            ClientDataReceiver cr = new ClientDataReceiver(message.uuid, message.location, message.name, message.dataSize);
            cr.start();
        }
        ClientDataReceiver.addBufferBytes(message.uuid, message.data);
        ResponseSender.sendToServer(SERVER_RESPONSE, 0, message.uuid, new CompoundNBT());
    }
}
