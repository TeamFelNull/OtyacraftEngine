package red.felnull.otyacraftengine.handler;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;
import red.felnull.otyacraftengine.api.registries.OERegistries;
import red.felnull.otyacraftengine.data.ReceiveTextureManager;
import red.felnull.otyacraftengine.packet.ReceiveTextureHashCheckMessage;
import red.felnull.otyacraftengine.util.IKSGFileLoadUtil;
import red.felnull.otyacraftengine.util.IKSGPathUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ReceiveTextureHashCheckMessageHandler {
    public static void reversiveMessage(ReceiveTextureHashCheckMessage message, Supplier<NetworkEvent.Context> ctx) {
        Map<ResourceLocation, List<String>> responseLists = new HashMap<>();
        message.checkblemap.entrySet().stream().filter(n -> OERegistries.TEXTUER_SEND_PATH.containsKey(n.getKey())).forEach(n -> {
            n.getValue().forEach((n2, m2) -> {
                File tf = IKSGPathUtil.getWorldSaveDataPath().resolve(OERegistries.TEXTUER_SEND_PATH.get(n.getKey()).resolve(n2)).toFile();
                boolean isUpdate = false;
                if (tf.exists()) {
                    try {
                        isUpdate = IKSGFileLoadUtil.getCheckSum(tf) != m2;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    isUpdate = m2 != 0;
                }
                if (isUpdate) {
                    if (!responseLists.containsKey(n.getKey()))
                        responseLists.put(n.getKey(), new ArrayList<>());
                    responseLists.get(n.getKey()).add(n2);
                }
            });
        });
        responseLists.forEach((n, m) -> {
            m.forEach(n2 -> {
                ReceiveTextureManager.instance().updateTextuerServer(ctx.get().getSender(), n, n2);
            });
        });

    }
}
