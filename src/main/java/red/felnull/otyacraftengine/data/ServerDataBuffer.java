package red.felnull.otyacraftengine.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ServerDataBuffer {
    private static ServerDataBuffer INSTANCE;
    private Map<String, Map<String, ByteDataBuffer>> dataBuffers = new HashMap<String, Map<String, ByteDataBuffer>>();

    public static ServerDataBuffer instance() {
        return INSTANCE;
    }

    public ByteDataBuffer getData(String pluuid, String uuid) {

        if (!dataBuffers.containsKey(pluuid) || !dataBuffers.get(pluuid).containsKey(uuid))
            return null;

        return dataBuffers.get(pluuid).get(uuid);
    }

    public void removeData(String pluuid, String uuid) {

        if (!dataBuffers.containsKey(pluuid) || !dataBuffers.get(pluuid).containsKey(uuid))
            return;

        dataBuffers.get(pluuid).remove(uuid);
    }

    public void addData(String pluuid, String uuid, ByteDataBuffer data) {

        if (!dataBuffers.containsKey(pluuid))
            dataBuffers.put(pluuid, new HashMap<String, ByteDataBuffer>());

        dataBuffers.get(pluuid).put(uuid, data);
    }

    public static void init() {
        INSTANCE = new ServerDataBuffer();
    }

    public void tick() {
        for (Map.Entry<String, Map<String, ByteDataBuffer>> pls : dataBuffers.entrySet()) {
            Set<String> deletes = new HashSet<String>();
            for (Map.Entry<String, ByteDataBuffer> datas : dataBuffers.get(pls).entrySet()) {
                if (datas.getValue().isTimeDelete()) {
                    datas.getValue().updateTime();
                    if (datas.getValue().canDelete())
                        deletes.add(datas.getKey());
                }
            }
            deletes.forEach(n -> removeData(pls.getKey(), n));
        }
    }
}
