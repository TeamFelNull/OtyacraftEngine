package red.felnull.otyacraftengine.client.data;

import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import red.felnull.otyacraftengine.throwable.SizeOverException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class WorldShareManager {
    private static final Logger LOGGER = LogManager.getLogger(WorldShareManager.class);
    private static final Minecraft mc = Minecraft.getInstance();
    private static final WorldShareManager INSTANCE = new WorldShareManager();
    private final Map<UUID, UploadData> UPLOAD_DATA = new HashMap<>();
    private WorldShareUploadThread uploadThread;

    public static WorldShareManager getInstance() {
        return INSTANCE;
    }

    public void upload(UUID id, byte[] data) throws SizeOverException {
        upload(id, data, n -> {
        });
    }

    public void upload(UUID id, byte[] data, Consumer<UploadListenerData> listener) throws SizeOverException {
        long length = data.length;
        long maxL = 1024L * 1024L;

        if (length > maxL)
            throw new SizeOverException(length, maxL);

        if (!UPLOAD_DATA.containsKey(id)) {
            listener.accept(new WorldShareManager.UploadListenerData(false, 0f));
            UPLOAD_DATA.put(id, new UploadData(data, listener));
        }
    }

    public void tick() {
        if (mc.level == null) {
            UPLOAD_DATA.clear();
            if (uploadThread != null) {
                uploadThread.stopped();
                uploadThread = null;
            }
            return;
        }

        if (!UPLOAD_DATA.isEmpty() && (uploadThread == null || uploadThread.isStop())) {
            Map.Entry<UUID, UploadData> entry = UPLOAD_DATA.entrySet().stream().findFirst().get();
            uploadThread = new WorldShareUploadThread(entry.getKey(), entry.getValue().data(), entry.getValue().listener());
            uploadThread.start();
            UPLOAD_DATA.remove(entry.getKey());
        }
    }

    public void onDownloadStartSuccess(UUID uuid) {
        if (uploadThread.uuid.equals(uuid))
            uploadThread.ready();
    }

    public void onDownloadStartError(UUID uuid, String exp) {
        LOGGER.error("Download Start Failure", new Exception(exp));
        if (uploadThread.uuid.equals(uuid))
            uploadThread.stopped();
    }

    public static record UploadData(byte[] data, Consumer<UploadListenerData> listener) {

    }

    public static record UploadListenerData(boolean doing, float progress) {
    }
}
