package red.felnull.otyacraftengine.data;

import net.minecraft.util.ResourceLocation;
import red.felnull.otyacraftengine.api.registries.OERegistries;
import red.felnull.otyacraftengine.util.FileLoadHelper;
import red.felnull.otyacraftengine.util.PathUtil;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ServerDataReceiver extends Thread {
    private static Map<String, Map<String, DataReceiverBuffer>> RECEIVS = new HashMap<String, Map<String, DataReceiverBuffer>>();
    private final String name;
    private final String uuid;
    private final ResourceLocation location;
    private final String playerUUID;
    private int cont;

    public ServerDataReceiver(String playerUUID, String uuid, ResourceLocation location, String name, int datasize) {
        this.playerUUID = playerUUID;
        this.uuid = uuid;
        this.location = location;
        this.name = name;
        if (!RECEIVS.containsKey(playerUUID)) {
            RECEIVS.put(playerUUID, new HashMap<String, DataReceiverBuffer>());
        }
        RECEIVS.get(playerUUID).put(uuid, new DataReceiverBuffer(datasize, uuid, location, name));
    }

    public void receiveFinish() {
        RECEIVS.get(playerUUID).remove(uuid);
    }

    public void run() {
        while (!RECEIVS.get(playerUUID).get(uuid).isPerfectByte()) {

            if (RECEIVS.get(playerUUID).get(uuid).stop /*|| (cont == RECEIVS.get(playerUUID).get(uuid).getCont())*/) {
                receiveFinish();
                return;
            }
            try {
                sleep(10);
            } catch (InterruptedException e) {
            }
        }
        FileLoadHelper.fileBytesWriter(RECEIVS.get(playerUUID).get(uuid).getBytes(), PathUtil.getWorldSaveDataPath().resolve(Paths.get(OERegistries.SERVER_RECEVED_PATH.get(location)).resolve(name)));
        receiveFinish();
    }

    public static void addBufferBytes(String pluuid, String uuid, byte[] bytes) {
        if (RECEIVS.containsKey(pluuid) && RECEIVS.get(pluuid).containsKey(uuid)) {
            RECEIVS.get(pluuid).get(uuid).addBytes(bytes);
        }
    }
}
