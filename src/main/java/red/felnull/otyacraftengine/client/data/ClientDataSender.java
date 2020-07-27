package red.felnull.otyacraftengine.client.data;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.api.event.SenderEvent;
import red.felnull.otyacraftengine.data.SendReceiveLogger;
import red.felnull.otyacraftengine.packet.ClientDataSendMessage;
import red.felnull.otyacraftengine.packet.PacketHandler;

import java.util.HashMap;
import java.util.Map;

public class ClientDataSender extends Thread {
    public static int max = 5;
    private static Map<String, ClientDataSender> SENDS = new HashMap<String, ClientDataSender>();
    private final String name;
    private final String uuid;
    private final ResourceLocation location;
    private byte[] sendingData;
    public int dataCont;
    public boolean response = false;
    private boolean stop;
    private static Minecraft mc = Minecraft.getInstance();
    private final SendReceiveLogger logger;
    private final long fristTime;
    private long logTime;
    private long lastResponseTime;
    private long time;

    public ClientDataSender(String uuid, ResourceLocation location, String name, byte[] data) {
        this.name = name;
        this.uuid = uuid;
        this.sendingData = data;
        this.location = location;
        String det = "UUID:" + uuid + " Location:" + location.toString() + " Name:" + name + " Size:" + (data == null ? "0" : data.length) + "byte";
        this.logger = new SendReceiveLogger(location.toString(), det, Dist.CLIENT, SendReceiveLogger.SndOrRec.SEND);
        this.fristTime = System.currentTimeMillis();
        this.logTime = System.currentTimeMillis();
        this.lastResponseTime = System.currentTimeMillis();
    }

    public void sendStart() {
        if (SENDS.size() >= max) {
            OtyacraftEngine.LOGGER.error("The data cont that can be sent at one time is exceeded : " + location.toString() + " : " + this.name);
            this.sendingData = null;
            this.logger.addStartFailureLogLine(new TranslationTextComponent("rslog.err.excessSimultaneousSending"));
            this.logger.addFinishLogLine(SendReceiveLogger.Result.FAILURE, System.currentTimeMillis() - fristTime, (sendingData == null ? 0 : sendingData.length));
            this.logger.createLog();
            return;
        }
        SENDS.put(uuid, this);
        this.start();
    }

    public void sentFinish(SendReceiveLogger.Result result) {
        this.logger.addFinishLogLine(result, System.currentTimeMillis() - fristTime, sendingData.length);
        sendingData = null;
        SENDS.remove(uuid);
        this.logger.createLog();
        MinecraftForge.EVENT_BUS.post(new SenderEvent.Client.Pos(uuid, location, name, result));
    }


    public void run() {
        try {
            if (sendingData == null) {
                OtyacraftEngine.LOGGER.info("Null Sender Data : " + location.toString() + " : " + this.name);
                this.logger.addStartFailureLogLine(new TranslationTextComponent("rslog.err.nulldata"));
                sentFinish(SendReceiveLogger.Result.FAILURE);
                return;
            }
            this.logger.addStartLogLine();
            MinecraftForge.EVENT_BUS.post(new SenderEvent.Client.Pre(uuid, location, name));
            boolean frist = true;
            int sendbyte = 1024 * 8;
            int soundbytelengt = sendingData.length;
            for (int i = 0; i < soundbytelengt; i += sendbyte) {
                byte[] sndingbyte = new byte[soundbytelengt - i >= sendbyte ? sendbyte : soundbytelengt - i];
                for (int c = 0; c < sendbyte; c++) {
                    if ((i + c) < soundbytelengt) {
                        sndingbyte[c] = sendingData[i + c];
                        dataCont++;
                    }
                }
                ClientDataSendMessage sendpacet = null;
                if (frist) {
                    sendpacet = new ClientDataSendMessage(uuid, location, name, sndingbyte, sendingData.length, true);
                } else {
                    sendpacet = new ClientDataSendMessage(uuid, sndingbyte);
                }
                PacketHandler.INSTANCE.sendToServer(sendpacet);
                sndingbyte = null;
                frist = false;
                time = System.currentTimeMillis();

                while (!response) {
                    if (System.currentTimeMillis() - logTime >= 3000) {
                        logTime = System.currentTimeMillis();
                        this.logger.addProgress(dataCont, sendingData.length - dataCont, System.currentTimeMillis() - fristTime, System.currentTimeMillis() - lastResponseTime, SendReceiveLogger.SndOrRec.SEND);
                    }

                    if (mc.player == null) {
                        this.logger.addLogLine(new TranslationTextComponent("rslog.err.playerExitedWorld"));
                        sentFinish(SendReceiveLogger.Result.FAILURE);
                        return;
                    }

                    if (stop) {
                        this.logger.addLogLine(new TranslationTextComponent("rslog.err.stop"));
                        sentFinish(SendReceiveLogger.Result.FAILURE);
                        return;
                    }

                    if (System.currentTimeMillis() - time >= 10000) {
                        this.logger.addLogLine(new TranslationTextComponent("rslog.err.timeout"));
                        sentFinish(SendReceiveLogger.Result.FAILURE);
                        return;
                    }
                    sleep(1);
                }
                response = false;
                lastResponseTime = System.currentTimeMillis();
            }
        } catch (Exception ex) {
            this.logger.addExceptionLogLine(ex);
            ex.printStackTrace();
            sentFinish(SendReceiveLogger.Result.FAILURE);
        }
        sentFinish(SendReceiveLogger.Result.SUCCESS);
    }

    public static void response(String uuid) {
        if (SENDS.containsKey(uuid)) {
            SENDS.get(uuid).response = true;
        }
    }

    public static void sending(String uuid, ResourceLocation location, String name, byte[] data) {
        ClientDataSender cds = new ClientDataSender(uuid, location, name, data);
        cds.sendStart();
    }
}
