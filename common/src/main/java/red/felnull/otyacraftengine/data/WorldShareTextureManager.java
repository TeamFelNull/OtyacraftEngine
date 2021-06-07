package red.felnull.otyacraftengine.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.api.SimpleMessageSender;
import red.felnull.otyacraftengine.throwable.AlreadyDownloadException;
import red.felnull.otyacraftengine.throwable.SizeOverException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WorldShareTextureManager {
    private static final ResourceLocation WSTM_MESSAGE_LOCATION = new ResourceLocation(OtyacraftEngine.MODID, "worldsharedownload");
    public static final Logger LOGGER = LogManager.getLogger(WorldShareTextureManager.class);
    private static final WorldShareTextureManager INSTANCE = new WorldShareTextureManager();
    private final Map<UUID, DownloadInstance> DOWNLOADS = new HashMap<>();
    private final int sendByte = 1024 * 8;
    private final int sendBffByte = 5;

    public static WorldShareTextureManager getInstance() {
        return INSTANCE;
    }

    public void start(UUID playerID, UUID id, int let) {
        try {
            if (DOWNLOADS.containsKey(playerID))
                throw new AlreadyDownloadException(DOWNLOADS.get(playerID).id);

            long maxL = 1024L * 1024L;
            if (let > maxL)
                throw new SizeOverException(let, maxL);

            DOWNLOADS.put(playerID, new DownloadInstance(id, let));

            CompoundTag tag = new CompoundTag();
            tag.putUUID("Id", id);
            SimpleMessageSender.sendToClient(playerID, WSTM_MESSAGE_LOCATION, 0, tag);

        } catch (Throwable ex) {
            ex.printStackTrace();
            CompoundTag tag = new CompoundTag();
            tag.putUUID("Id", id);
            tag.putString("Exception", ex.getMessage());
            SimpleMessageSender.sendToClient(playerID, WSTM_MESSAGE_LOCATION, 1, tag);
        }
    }

    public void receiveBuffStart(UUID playerID, UUID id, int num, byte[] hash) {
        try {
            if (DOWNLOADS.containsKey(playerID)) {
                DOWNLOADS.get(id).hashData = new DLHashData(num, hash);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void receive(UUID playerID, UUID id, int num, byte[] data) {
        try {

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private static class DownloadInstance {
        private final UUID id;
        private final byte[] data;
        private byte[] bfdata;
        private DLHashData hashData;

        public DownloadInstance(UUID id, int let) {
            this.id = id;
            this.data = new byte[let];
        }
    }

    private static record DLHashData(int num, byte[] hash) {

    }
}
