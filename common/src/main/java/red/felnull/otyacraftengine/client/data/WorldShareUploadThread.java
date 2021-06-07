package red.felnull.otyacraftengine.client.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.ArrayUtils;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.api.SimpleMessageSender;
import red.felnull.otyacraftengine.util.IKSGDataUtil;

import java.util.UUID;
import java.util.function.Consumer;

public class WorldShareUploadThread extends Thread {
    private static final ResourceLocation WST_MESSAGE_LOCATION = new ResourceLocation(OtyacraftEngine.MODID, "worldshareupload");
    public final UUID uuid;
    private final byte[] data;
    private final Consumer<WorldShareManager.UploadListenerData> listener;
    private final int sendByte = 1024 * 8;
    private boolean stop;
    private boolean ready;


    public WorldShareUploadThread(UUID uuid, byte[] data, Consumer<WorldShareManager.UploadListenerData> listener) {
        this.uuid = uuid;
        this.data = data;
        this.listener = listener;
    }

    @Override
    public void run() {
        try {
            progressed(0f);
            CompoundTag stTag = new CompoundTag();
            stTag.putUUID("Id", uuid);
            stTag.putInt("Length", data.length);
            SimpleMessageSender.sendToServer(WST_MESSAGE_LOCATION, 0, stTag);

            while (!ready) {
                if (stop)
                    return;

                if (ready)
                    break;

                Thread.sleep(10);
            }

            int allCont = data.length / sendByte;
            allCont += data.length % sendByte == 0 ? 0 : 1;

            int sncont = 0;
            int oneSc = 5;
            int sc = allCont / oneSc;
            sc += allCont % oneSc == 0 ? 0 : 1;
            int cont = 0;
            for (int i = 0; i < sc; i++) {
                int sndB = Math.min(oneSc, allCont - sncont);
                int num = sncont;
                sncont += sndB;
                byte[] hashedData = null;
                byte[][] sdats = new byte[sndB][];
                for (int j = 0; j < sndB; j++) {
                    byte[] sendData = new byte[Math.min(sendByte, data.length - cont)];
                    System.arraycopy(data, cont, sendData, 0, sendData.length);
                    cont += sendData.length;
                    hashedData = ArrayUtils.addAll(hashedData, sendData);
                    sdats[j] = sendData;
                }

                CompoundTag tag1 = new CompoundTag();
                tag1.putUUID("Id", uuid);
                tag1.putByteArray("Hash", IKSGDataUtil.createMD5Hash(hashedData));
                tag1.putInt("Num", num);
                SimpleMessageSender.sendToServer(WST_MESSAGE_LOCATION, 1, tag1);
                for (byte[] sdat : sdats) {

                }
                Thread.sleep(10);
            }
            System.out.println(cont + ":" + data.length);

            stop = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void progressed(float pr) {
        listener.accept(new WorldShareManager.UploadListenerData(true, pr));
    }

    protected boolean isStop() {
        return stop;
    }

    protected void stopped() {
        stop = true;
    }

    protected void ready() {
        ready = true;
    }
}
