package red.felnull.otyacraftengine.handler;

import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;
import red.felnull.otyacraftengine.api.registries.OERegistries;
import red.felnull.otyacraftengine.packet.ReceiveTextureHashCheckMessage;

import java.nio.file.Path;
import java.util.function.Supplier;

public class ReceiveTextureHashCheckMessageHandler {
    public static void reversiveMessage(ReceiveTextureHashCheckMessage message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().getSender().sendStatusMessage(new StringTextComponent("test= " + message.checkblemap.toString()), false);
        message.checkblemap.entrySet().stream().filter(n -> OERegistries.TEXTUER_SEND_PATH.containsKey(n.getKey())).forEach(n -> {
            n.getValue().forEach((n2, m2) -> {
                Path tp = OERegistries.TEXTUER_SEND_PATH.get(n.getKey()).resolve(n2);
                ctx.get().getSender().sendStatusMessage(new StringTextComponent(tp.toAbsolutePath().toString()), false);
            });
        });
    }
}
