package red.felnull.otyacraftengine.data;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.network.PacketDistributor;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.api.event.SenderEvent;
import red.felnull.otyacraftengine.packet.PacketHandler;
import red.felnull.otyacraftengine.packet.ServerDataSendMessage;
import red.felnull.otyacraftengine.util.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.zip.GZIPOutputStream;

public class ServerDataSender extends Thread {
    public static int max = 5;
    private static Map<String, Map<String, ServerDataSender>> SENDS = new HashMap<String, Map<String, ServerDataSender>>();
    private final String playerUUID;
    private final String name;
    private final String uuid;
    private final ResourceLocation location;
    private byte[] sendingData;
    public int dataCont;
    private long time;
    public boolean response = false;
    private long logTime;
    private boolean stop;
    private long lastResponseTime;
    private final long fristTime;
    private final SendReceiveLogger logger;

    public ServerDataSender(String playerUUID, String uuid, ResourceLocation location, String name, byte[] data) {
        this.playerUUID = playerUUID;
        this.uuid = uuid;
        this.location = location;
        this.name = name;
        this.sendingData = data;
        this.logTime = System.currentTimeMillis();
        this.lastResponseTime = System.currentTimeMillis();
        this.fristTime = System.currentTimeMillis();
        String det = "PlayerUUID:" + playerUUID + " UUID:" + uuid + " Location:" + location.toString() + " Name:" + name + " Size:" + (data == null ? "0" : data.length) + "byte";
        this.logger = new SendReceiveLogger(location.toString(), det, Dist.DEDICATED_SERVER, SendReceiveLogger.SndOrRec.SEND);
    }

    public static boolean isMaxSending(String playerUUID) {

        if (!SENDS.containsKey(playerUUID))
            return false;

        return SENDS.get(playerUUID).size() >= max;
    }

    public void sendStart() {
        if (!SENDS.containsKey(playerUUID)) {
            SENDS.put(playerUUID, new HashMap<String, ServerDataSender>());
        }
        if (isMaxSending(playerUUID)) {
            OtyacraftEngine.LOGGER.error("The data cont that can be sent at one time is exceeded : " + location.toString() + " : " + this.name);
            this.sendingData = null;
            this.logger.addStartFailureLogLine(new TranslationTextComponent("rslog.err.excessSimultaneousSending"));
            this.logger.addFinishLogLine(SendReceiveLogger.Result.FAILURE, System.currentTimeMillis() - fristTime, (sendingData == null ? 0 : sendingData.length));
            this.logger.createLog();
            return;
        }
        SENDS.get(playerUUID).put(uuid, this);
        this.start();
    }

    public void sentFinish(SendReceiveLogger.Result result) {
        this.logger.addFinishLogLine(result, System.currentTimeMillis() - fristTime, sendingData.length);
        sendingData = null;
        SENDS.get(playerUUID).remove(uuid);
        this.logger.createLog();
        MinecraftForge.EVENT_BUS.post(new SenderEvent.Server.Pos(ServerHelper.getMinecraftServer().getPlayerList().getPlayerByUUID(UUID.fromString(playerUUID)), uuid, location, name, result));
    }

    public void run() {
        try {
            if (sendingData == null) {
                OtyacraftEngine.LOGGER.info("Null Sender Data : " + location.toString() + " : " + this.name);
                this.logger.addStartFailureLogLine(new TranslationTextComponent("rslog.err.nulldata"));
                sentFinish(SendReceiveLogger.Result.FAILURE);
            }
            this.logger.addStartLogLine();
            MinecraftForge.EVENT_BUS.post(new SenderEvent.Server.Pre(ServerHelper.getMinecraftServer().getPlayerList().getPlayerByUUID(UUID.fromString(playerUUID)), uuid, location, name));
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
                ServerDataSendMessage sendpacet = null;
                if (frist) {
                    sendpacet = new ServerDataSendMessage(uuid, location, name, sndingbyte, sendingData.length, true);
                } else {
                    sendpacet = new ServerDataSendMessage(uuid, sndingbyte);
                }
                PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> PlayerHelper.getPlayerByUUID(playerUUID)), sendpacet);
                sndingbyte = null;
                frist = false;
                time = System.currentTimeMillis();
                while (!response) {
                    if (System.currentTimeMillis() - logTime >= 3000) {
                        this.logger.addProgress(dataCont, sendingData.length - dataCont, System.currentTimeMillis() - fristTime, System.currentTimeMillis() - lastResponseTime, SendReceiveLogger.SndOrRec.SEND);
                        logTime = System.currentTimeMillis();
                    }

                    if (!ServerHelper.isOnlinePlayer(playerUUID)) {
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

    public static void response(String playerUUID, String uuid) {
        if (SENDS.containsKey(playerUUID) && SENDS.get(playerUUID).containsKey(uuid)) {
            SENDS.get(playerUUID).get(uuid).response = true;
        }
    }

    public static void sending(String playerUUID, String uuid, ResourceLocation location, String name, byte[] data) {
        ServerDataSender sds = new ServerDataSender(playerUUID, uuid, location, name, data);
        sds.sendStart();
    }

    public static void srlogsGziping() {

        File[] files = PathUtil.getWorldSaveDataPath().resolve(Paths.get("srlogs")).toFile().listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return StringHelper.getExtension(file.getName()).equals("log");
            }
        });
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
                byte[] bytes = FileLoadHelper.fileBytesReader(file.toPath());
                GZIPOutputStream gzip_out = new GZIPOutputStream(out);
                gzip_out.write(bytes);
                gzip_out.close();
            }
            out.close();
            byte[] ret = out.toByteArray();

            String name = "";
            if (mostold == mostnew) {
                name = mostnew.getName();
            } else {
                name = StringHelper.deleteExtension(mostold.getName()) + "~" + StringHelper.deleteExtension(mostnew.getName());
            }
            FileLoadHelper.fileBytesWriter(ret, PathUtil.getWorldSaveDataPath().resolve(Paths.get("srlogs\\" + name + ".log.gz")));
        } catch (IOException e) {
        }
        for (File file : files) {
            FileLoadHelper.deleteFile(file);
        }
    }
}
