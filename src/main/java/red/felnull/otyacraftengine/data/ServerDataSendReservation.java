package red.felnull.otyacraftengine.data;

import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class ServerDataSendReservation {
    public static Map<String, Map<String, DataSendReservationBuffer>> RESERVATION = new HashMap<String, Map<String, DataSendReservationBuffer>>();

    public static void add(String plUuid, String uuid, ResourceLocation location, String name, byte[] data) {
        if (!RESERVATION.containsKey(plUuid))
            RESERVATION.put(plUuid, new HashMap<String, DataSendReservationBuffer>());

        RESERVATION.get(plUuid).put(uuid, new DataSendReservationBuffer(location, name, data));
    }

    public static void tick() {
        if (RESERVATION.isEmpty())
            return;

        RESERVATION.entrySet().forEach(n -> {
            if (!n.getValue().isEmpty() && !ServerDataSender.isMaxSending(n.getKey())) {
                String uuid = null;
                for (Map.Entry<String, DataSendReservationBuffer> en : n.getValue().entrySet()) {
                    ServerDataSender.sending(n.getKey(), en.getKey(), en.getValue().location, en.getValue().name, en.getValue().data);
                    uuid = en.getKey();
                    break;
                }
                n.getValue().remove(uuid);
            }
        });


    }
}
