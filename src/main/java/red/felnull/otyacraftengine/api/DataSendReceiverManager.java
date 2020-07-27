package red.felnull.otyacraftengine.api;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import red.felnull.otyacraftengine.client.data.ClientDataSender;
import red.felnull.otyacraftengine.data.ServerDataSender;
import red.felnull.otyacraftengine.util.PlayerHelper;

import java.util.UUID;

public class DataSendReceiverManager {
    private static DataSendReceiverManager INSTANCE;

    public static void init() {
        INSTANCE = new DataSendReceiverManager();
    }

    public static DataSendReceiverManager instance() {
        return INSTANCE;
    }

    public String sendToServer(ResourceLocation location, String name, byte[] data) {
        String uuid = UUID.randomUUID().toString();
        ClientDataSender.sending(uuid, location, name, data);
        return uuid;
    }

    public String sendToClient(ServerPlayerEntity player, ResourceLocation location, String name, byte[] data) {
        String uuid = UUID.randomUUID().toString();
        ServerDataSender.sending(PlayerHelper.getUUID(player), uuid, location, name, data);
        return uuid;
    }
}
