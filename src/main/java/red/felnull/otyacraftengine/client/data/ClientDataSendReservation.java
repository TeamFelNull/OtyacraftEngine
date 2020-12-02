package red.felnull.otyacraftengine.client.data;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import red.felnull.otyacraftengine.data.DataSendReservationBuffer;

import java.util.HashMap;
import java.util.Map;

public class ClientDataSendReservation {
    public static Map<String, DataSendReservationBuffer> RESERVATION = new HashMap<String, DataSendReservationBuffer>();

    @OnlyIn(Dist.CLIENT)
    public static void add(String uuid, ResourceLocation location, String name, byte[] data) {
        RESERVATION.put(uuid, new DataSendReservationBuffer(location, name, data));
    }

    @OnlyIn(Dist.CLIENT)
    public static void tick() {
        if (RESERVATION.isEmpty() || ClientDataSender.isMaxSending())
            return;
        String uuid = null;
        for (Map.Entry<String, DataSendReservationBuffer> en : RESERVATION.entrySet()) {
            ClientDataSender.sending(en.getKey(), en.getValue().location, en.getValue().name, en.getValue().data);
            uuid = en.getKey();
            break;
        }
        RESERVATION.remove(uuid);
    }
}
