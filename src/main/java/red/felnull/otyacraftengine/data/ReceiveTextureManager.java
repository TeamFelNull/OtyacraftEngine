package red.felnull.otyacraftengine.data;


import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.api.DataSendReceiverManager;
import red.felnull.otyacraftengine.api.ResponseSender;
import red.felnull.otyacraftengine.api.registries.OERegistries;
import red.felnull.otyacraftengine.util.IKSGFileLoadUtil;
import red.felnull.otyacraftengine.util.IKSGPathUtil;
import red.felnull.otyacraftengine.util.IKSGServerUtil;

import java.nio.file.Path;
import java.util.UUID;

public class ReceiveTextureManager {

    private static ReceiveTextureManager INSTANCE;


    public static ReceiveTextureManager instance() {
        return INSTANCE;
    }

    public static void init() {
        INSTANCE = new ReceiveTextureManager();
    }


    public void requestedTextuerSendServer(String indexnaxme, ServerPlayerEntity player, ResourceLocation
            location, String name) {
        Path ImagePath = null;
        if (OERegistries.TEXTUER_SEND_PATH.containsKey(location)) {
            ImagePath = IKSGPathUtil.getWorldSaveDataPath().resolve(OERegistries.TEXTUER_SEND_PATH.get(location)).resolve(name);
        }
        if (ImagePath != null && ImagePath.toFile().exists()) {
            byte[] data = IKSGFileLoadUtil.fileBytesReader(ImagePath);
            String id = DataSendReceiverManager.instance().sendToClient(player, new ResourceLocation(OtyacraftEngine.MODID, "textuerrequest"), UUID.randomUUID().toString(), data);
            CompoundNBT tag = new CompoundNBT();
            tag.putString("index", indexnaxme);
            ResponseSender.sendToClient(player, new ResourceLocation(OtyacraftEngine.MODID, "textuerrequest"), 0, id, tag);
        } else {
            ResponseSender.sendToClient(player, new ResourceLocation(OtyacraftEngine.MODID, "textuerrequest"), 2, indexnaxme, new CompoundNBT());
        }
    }

    public void updateTextuerServer(ResourceLocation location, String name) {
        IKSGServerUtil.getOnlinePlayers().forEach(n -> updateTextuerServer(n, location, name));
    }

    public void updateTextuerServer(ServerPlayerEntity player, ResourceLocation location, String name) {
        CompoundNBT tag = new CompoundNBT();
        tag.putString("name", name);
        ResponseSender.sendToClient(player, new ResourceLocation(OtyacraftEngine.MODID, "textuerrequest"), 1, location.toString(), tag);
    }

}
