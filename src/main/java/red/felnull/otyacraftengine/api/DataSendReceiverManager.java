package red.felnull.otyacraftengine.api;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import red.felnull.otyacraftengine.client.data.ClientDataSendReservation;
import red.felnull.otyacraftengine.client.data.ClientDataSender;
import red.felnull.otyacraftengine.data.ServerDataSendReservation;
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
        if (ClientDataSender.isMaxSending()) {
            ClientDataSendReservation.add(uuid, location, name, data);
        } else {
            ClientDataSender.sending(uuid, location, name, data);
        }
        return uuid;
    }

    public String sendToClient(ServerPlayerEntity player, ResourceLocation location, String name, byte[] data) {
        String uuid = UUID.randomUUID().toString();
        String pluuid = PlayerHelper.getUUID(player);
        if (ServerDataSender.isMaxSending(pluuid)) {
            ServerDataSendReservation.add(pluuid, uuid, location, name, data);
        } else {
            ServerDataSender.sending(pluuid, uuid, location, name, data);
        }
        return uuid;
    }
}
