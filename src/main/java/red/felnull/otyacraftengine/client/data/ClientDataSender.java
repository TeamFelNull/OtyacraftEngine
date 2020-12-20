package red.felnull.otyacraftengine.client.data;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.api.event.common.SenderEvent;
import red.felnull.otyacraftengine.data.SendReceiveLogger;
import red.felnull.otyacraftengine.packet.ClientDataSendMessage;
import red.felnull.otyacraftengine.packet.PacketHandler;
import red.felnull.otyacraftengine.util.IKSGFileLoadUtil;
import red.felnull.otyacraftengine.util.IKSGStringUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.zip.GZIPOutputStream;

public class ClientDataSender extends Thread {
    public static int max = 5;
    private static final Map<String, ClientDataSender> SENDS = new HashMap<>();
    private static final Minecraft mc = Minecraft.getInstance();
    private final String name;
    private final String uuid;
    private final ResourceLocation location;
    private final SendReceiveLogger logger;
    private final long fristTime;
    public int dataCont;
    public boolean response = false;
    private byte[] sendingData;
    private boolean stop;
    private long logTime;
    private long lastResponseTime;
    private long time;

    @OnlyIn(Dist.CLIENT)
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

    @OnlyIn(Dist.CLIENT)
    public static boolean isMaxSending() {
        return SENDS.size() >= max;
    }

    @OnlyIn(Dist.CLIENT)
    public static void response(String uuid) {
        if (SENDS.containsKey(uuid)) {
            SENDS.get(uuid).response = true;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void sending(String uuid, ResourceLocation location, String name, byte[] data) {
        ClientDataSender cds = new ClientDataSender(uuid, location, name, data);
        cds.sendStart();
    }

    @OnlyIn(Dist.CLIENT)
    public static void srlogsGziping() {

        if (!Paths.get("srlogs").toFile().exists())
            return;

        File[] files = Paths.get("srlogs").toFile().listFiles(file -> IKSGStringUtil.getExtension(file.getName()).equals("log"));
        if (files.length == 0)
            return;

        File mostold = null;
        File mostnew = null;

        for (File file : files) {

            if (mostold == null || mostnew == null) {
                mostold = file;
                mostnew = file;
            } else {
                if (mostold.lastModified() < file.lastModified()) {
                    mostold = file;
                }
                if (mostnew.lastModified() > file.lastModified()) {
                    mostnew = file;
                }
            }
        }

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            for (File file : files) {
                byte[] bytes = IKSGFileLoadUtil.fileBytesReader(file.toPath());
                GZIPOutputStream gzip_out = new GZIPOutputStream(out);
                gzip_out.write(Objects.requireNonNull(bytes));
                gzip_out.close();
            }
            out.close();
            byte[] ret = out.toByteArray();

            String name;
            if (mostold == mostnew) {
                name = mostnew.getName();
            } else {
                name = IKSGStringUtil.deleteExtension(mostold.getName()) + "~" + IKSGStringUtil.deleteExtension(mostnew.getName());
            }
            IKSGFileLoadUtil.fileBytesWriter(ret, Paths.get("srlogs\\" + name + ".log.gz"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        for (File file : files) {
            IKSGFileLoadUtil.deleteFile(file);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void sendStart() {
        if (isMaxSending()) {
            OtyacraftEngine.LOGGER.error("The data cont that can be sent at one time is exceeded : " + location.toString() + " : " + this.name);
            this.sendingData = null;
            this.logger.addStartFailureLogLine(new TranslationTextComponent("rslog.err.excessSimultaneousSending"));
            this.logger.addFinishLogLine(SendReceiveLogger.SRResult.FAILURE, System.currentTimeMillis() - fristTime, (sendingData == null ? 0 : sendingData.length));
            this.logger.createLog();
            return;
        }
        SENDS.put(uuid, this);
        this.start();
    }

    @OnlyIn(Dist.CLIENT)
    public void sentFinish(SendReceiveLogger.SRResult result) {
        this.logger.addFinishLogLine(result, System.currentTimeMillis() - fristTime, sendingData.length);
        sendingData = null;
        SENDS.remove(uuid);
        this.logger.createLog();
        MinecraftForge.EVENT_BUS.post(new SenderEvent.Client.Pos(uuid, location, name, result));
    }

    @OnlyIn(Dist.CLIENT)
    public void run() {
        try {
            if (sendingData == null) {
                OtyacraftEngine.LOGGER.info("Null Sender Data : " + location.toString() + " : " + this.name);
                this.logger.addStartFailureLogLine(new TranslationTextComponent("rslog.err.nulldata"));
                sentFinish(SendReceiveLogger.SRResult.FAILURE);
                return;
            }
            this.logger.addStartLogLine();
            MinecraftForge.EVENT_BUS.post(new SenderEvent.Client.Pre(uuid, location, name));
            boolean frist = true;
            int sendbyte = 1024 * 8;
            int soundbytelengt = sendingData.length;
            for (int i = 0; i < soundbytelengt; i += sendbyte) {
                byte[] sndingbyte = new byte[Math.min(soundbytelengt - i, sendbyte)];
                System.arraycopy(sendingData, i, sndingbyte, 0, sndingbyte.length);
                dataCont += sndingbyte.length;

                ClientDataSendMessage sendpacet;
                if (frist) {
                    sendpacet = new ClientDataSendMessage(uuid, location, name, sndingbyte, sendingData.length, true);
                } else {
                    sendpacet = new ClientDataSendMessage(uuid, sndingbyte);
                }
                PacketHandler.INSTANCE.sendToServer(sendpacet);
                MinecraftForge.EVENT_BUS.post(new SenderEvent.Client.Run(uuid, location, name, sendingData.length, dataCont));

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
                        sentFinish(SendReceiveLogger.SRResult.FAILURE);
                        return;
                    }

                    if (stop) {
                        this.logger.addLogLine(new TranslationTextComponent("rslog.err.stop"));
                        sentFinish(SendReceiveLogger.SRResult.FAILURE);
                        return;
                    }

                    if (System.currentTimeMillis() - time >= 10000) {
                        this.logger.addLogLine(new TranslationTextComponent("rslog.err.timeout"));
                        sentFinish(SendReceiveLogger.SRResult.FAILURE);
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
            sentFinish(SendReceiveLogger.SRResult.FAILURE);
        }
        sentFinish(SendReceiveLogger.SRResult.SUCCESS);
    }
}
