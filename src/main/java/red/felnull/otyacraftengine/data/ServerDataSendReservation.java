package red.felnull.otyacraftengine.data;

import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class ServerDataSendReservation {
    public static final Map<String, Map<String, DataSendReservationBuffer>> RESERVATION = new HashMap<>();

    public static void add(String plUuid, String uuid, ResourceLocation location, String name, byte[] data) {
        if (!RESERVATION.containsKey(plUuid))
            RESERVATION.put(plUuid, new HashMap<>());

        RESERVATION.get(plUuid).put(uuid, new DataSendReservationBuffer(location, name, data));
    }

    public static void tick() {
        if (RESERVATION.isEmpty())
            return;

        RESERVATION.forEach((key, value) -> {
            if (!value.isEmpty() && !ServerDataSender.isMaxSending(key)) {
                String uuid = null;
                for (Map.Entry<String, DataSendReservationBuffer> en : value.entrySet()) {
                    ServerDataSender.sending(key, en.getKey(), en.getValue().location, en.getValue().name, en.getValue().data);
                    uuid = en.getKey();
                    break;
                }
                value.remove(uuid);
            }
        });


    }
}
