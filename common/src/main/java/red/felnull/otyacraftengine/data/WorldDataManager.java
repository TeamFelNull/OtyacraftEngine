package red.felnull.otyacraftengine.data;


import net.minecraft.nbt.NbtIo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import red.felnull.otyacraftengine.util.IKSGPathUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * ワールドに保存するセーブデータの管理
 *
 * @author MORIMORI0317
 * @since 1.0
 */
public class WorldDataManager {
    public static final Logger LOGGER = LogManager.getLogger(WorldDataManager.class);
    private static final WorldDataManager INSTANCE = new WorldDataManager();
    private final Map<ResourceLocation, IkisugiSaveData> SAVE_DATAS = new HashMap<>();

    public static WorldDataManager getInstance() {
        return INSTANCE;
    }

    public void register(ResourceLocation location, IkisugiSaveData saveData) {
        SAVE_DATAS.put(location, saveData);
    }

    public <T extends IkisugiSaveData> T getSaveData(Class<T> aTClass) {
        for (IkisugiSaveData value : SAVE_DATAS.values()) {
            if (value.getClass() == aTClass) {
                return (T) value;
            }
        }
        return null;
    }

    public void save(MinecraftServer server) {
        SAVE_DATAS.forEach((n, m) -> {
            try {
                Path filePat = m.getSavePath() != null ? m.getSavePath() : Paths.get(n.getPath() + ".dat");
                File file = IKSGPathUtil.getWorldSaveDataPath(server).resolve(n.getNamespace()).resolve(filePat).toFile();
                file.toPath().getParent().toFile().mkdirs();
                m.save(file);
            } catch (Exception e) {
                LOGGER.error("World save data save failure: [" + n + "]", e);
            }
        });
    }

    public void load(MinecraftServer server) {
        SAVE_DATAS.forEach((n, m) -> {
            try {
                m.clear();
                Path filePat = m.getSavePath() != null ? m.getSavePath() : Paths.get(n.getPath() + ".dat");
                File file = IKSGPathUtil.getWorldSaveDataPath(server).resolve(n.getNamespace()).resolve(filePat).toFile();
                if (file.exists()) {
                    m.load(NbtIo.readCompressed(file).getCompound("data"));
                }
            } catch (IOException e) {
                LOGGER.error("World save data load failure: [" + n + "]", e);
            }
        });
    }

    public void unload() {
        SAVE_DATAS.forEach((n, m) -> m.clear());
    }

}
