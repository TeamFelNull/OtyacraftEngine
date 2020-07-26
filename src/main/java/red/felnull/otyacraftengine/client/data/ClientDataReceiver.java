package red.felnull.otyacraftengine.client.data;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import red.felnull.otyacraftengine.api.registries.OERegistries;
import red.felnull.otyacraftengine.data.DataReceiverBuffer;
import red.felnull.otyacraftengine.data.SendReceiveLogger;
import red.felnull.otyacraftengine.util.FileLoadHelper;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ClientDataReceiver extends Thread {
    private static Map<String, DataReceiverBuffer> RECEIVS = new HashMap<String, DataReceiverBuffer>();
    private static Minecraft mc = Minecraft.getInstance();
    private final String name;
    private final String uuid;
    private final ResourceLocation location;
    private int cont;
    private long fristTime;
    private final SendReceiveLogger logger;
    private long logTime;

    public ClientDataReceiver(String uuid, ResourceLocation location, String name, int datasize) {
        this.uuid = uuid;
        this.location = location;
        this.name = name;
        this.fristTime = System.currentTimeMillis();
        this.logTime = System.currentTimeMillis();
        RECEIVS.put(uuid, new DataReceiverBuffer(datasize, uuid, location, name));
        String det = "UUID:" + uuid + " Location:" + location.toString() + " Name:" + name + " Size:" + datasize + "byte";
        this.logger = new SendReceiveLogger(location.toString(), det, Dist.CLIENT, SendReceiveLogger.SndOrRec.RECEIVE);
    }

    public void receiveFinish(SendReceiveLogger.Result result) {
        this.logger.addFinishLogLine(result, System.currentTimeMillis() - fristTime, RECEIVS.get(uuid).getCont());
        RECEIVS.remove(uuid);
        this.logger.createLog();
    }

    public void run() {
        try {
            this.logger.addStartLogLine();
            long time = System.currentTimeMillis();
            while (!RECEIVS.get(uuid).isPerfectByte()) {
                if (System.currentTimeMillis() - logTime >= 3000) {
                    this.logger.addProgress(RECEIVS.get(uuid).getCont(), RECEIVS.get(uuid).allcont - RECEIVS.get(uuid).getCont(), System.currentTimeMillis() - fristTime, System.currentTimeMillis() - time, SendReceiveLogger.SndOrRec.RECEIVE);
                    logTime = System.currentTimeMillis();
                }
                if (RECEIVS.get(uuid).stop) {
                    this.logger.addLogLine(new TranslationTextComponent("rslog.err.stop"));
                    receiveFinish(SendReceiveLogger.Result.FAILURE);
                    return;
                }
                if (mc.player == null) {
                    this.logger.addLogLine(new TranslationTextComponent("rslog.err.playerExitedWorld"));
                    receiveFinish(SendReceiveLogger.Result.FAILURE);
                    return;
                }
                if ((cont == RECEIVS.get(uuid).getCont() && System.currentTimeMillis() - time >= 10000)) {
                    this.logger.addLogLine(new TranslationTextComponent("rslog.err.timeout"));
                    receiveFinish(SendReceiveLogger.Result.FAILURE);
                    return;
                }
                if (cont != RECEIVS.get(uuid).getCont()) {
                    cont = RECEIVS.get(uuid).getCont();
                    time = System.currentTimeMillis();
                }
                sleep(1);
            }
            FileLoadHelper.fileBytesWriter(RECEIVS.get(uuid).getBytes(), Paths.get(OERegistries.CLIENT_RECEVED_PATH.get(location)).resolve(name));
        } catch (Exception ex) {
            this.logger.addExceptionLogLine(ex);
            ex.printStackTrace();
            receiveFinish(SendReceiveLogger.Result.FAILURE);
        }
        receiveFinish(SendReceiveLogger.Result.SUCCESS);
    }

    public static void addBufferBytes(String uuid, byte[] bytes) {
        if (RECEIVS.containsKey(uuid)) {
            RECEIVS.get(uuid).addBytes(bytes);
        }
    }
}
