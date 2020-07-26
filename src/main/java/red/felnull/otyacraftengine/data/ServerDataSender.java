package red.felnull.otyacraftengine.data;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.PacketDistributor;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.packet.PacketHandler;
import red.felnull.otyacraftengine.packet.ServerDataSendMessage;
import red.felnull.otyacraftengine.util.PlayerHelper;
import red.felnull.otyacraftengine.util.ServerHelper;

import java.util.HashMap;
import java.util.Map;

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

    public ServerDataSender(String playerUUID, String uuid, ResourceLocation location, String name, byte[] data) {
        this.playerUUID = playerUUID;
        this.uuid = uuid;
        this.location = location;
        this.name = name;
        this.sendingData = data;
        this.logTime = System.currentTimeMillis();
        this.lastResponseTime = System.currentTimeMillis();
        this.fristTime = System.currentTimeMillis();
    }

    public void sendStart() {
        if (!SENDS.containsKey(playerUUID)) {
            SENDS.put(playerUUID, new HashMap<String, ServerDataSender>());
        }
        if (SENDS.get(playerUUID).size() >= max) {
            OtyacraftEngine.LOGGER.error("The data cont that can be sent at one time is exceeded : " + location.toString() + " : " + this.name);
            this.sendingData = null;
            return;
        }
        SENDS.get(playerUUID).put(uuid, this);
        this.start();
    }

    public void sentFinish(SendReceiveLogger.Result result) {
        sendingData = null;
        SENDS.get(playerUUID).remove(uuid);
    }

    public void run() {
        try {
            if (sendingData == null) {
                OtyacraftEngine.LOGGER.info("Null Sender Data : " + location.toString() + " : " + this.name);
                sentFinish(SendReceiveLogger.Result.FAILURE);
            }
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
                        logTime = System.currentTimeMillis();
                    }

                    if (!ServerHelper.isOnlinePlayer(playerUUID)) {
                        sentFinish(SendReceiveLogger.Result.FAILURE);
                        return;
                    }

                    if (stop) {
                        sentFinish(SendReceiveLogger.Result.FAILURE);
                        return;
                    }
                    if (System.currentTimeMillis() - time >= 10000) {
                        sentFinish(SendReceiveLogger.Result.FAILURE);
                        return;
                    }
                    sleep(1);
                }
                response = false;
                lastResponseTime = System.currentTimeMillis();
            }


        } catch (Exception ex) {
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
}
