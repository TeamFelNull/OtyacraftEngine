package red.felnull.otyacraftengine.handler;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.api.ResponseSender;
import red.felnull.otyacraftengine.data.ServerDataReceiver;
import red.felnull.otyacraftengine.packet.ClientDataSendMessage;
import red.felnull.otyacraftengine.util.IKSGPlayerUtil;

import java.util.function.Supplier;

public class ClientDataSendMessageHandler {
    private static final ResourceLocation CLIENT_RESPONSE = new ResourceLocation(OtyacraftEngine.MODID, "client_response");

    public static void reversiveMessage(ClientDataSendMessage message, Supplier<NetworkEvent.Context> ctx) {
        if (message.frist) {
            ServerDataReceiver dr = new ServerDataReceiver(IKSGPlayerUtil.getUUID(ctx.get().getSender()), message.uuid, message.location, message.name, message.dataSize);
            dr.start();
        }
        ServerDataReceiver.addBufferBytes(IKSGPlayerUtil.getUUID(ctx.get().getSender()), message.uuid, message.data);
        ResponseSender.sendToClient(ctx.get().getSender(), CLIENT_RESPONSE, 0, message.uuid, new CompoundNBT());
    }
}
