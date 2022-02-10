package dev.felnull.otyacraftengine.server.data;

import dev.felnull.otyacraftengine.util.OEPaths;
import net.minecraft.nbt.NbtIo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class WorldDataManager {
    public static final Logger LOGGER = LogManager.getLogger(WorldDataManager.class);
    private static final WorldDataManager INSTANCE = new WorldDataManager();
    private final Map<ResourceLocation, OEBaseSaveData> SAVE_DATA = new HashMap<>();

    public static WorldDataManager getInstance() {
        return INSTANCE;
    }

    public void register(ResourceLocation location, OEBaseSaveData saveData) {
        SAVE_DATA.put(location, saveData);
    }

    public <T extends OEBaseSaveData> T getSaveData(Class<T> aTClass) {
        for (OEBaseSaveData value : SAVE_DATA.values()) {
            if (value.getClass() == aTClass) {
                return (T) value;
            }
        }
        return null;
    }

    public void save(MinecraftServer server) {
        SAVE_DATA.forEach((n, m) -> {
            try {
                Path filePat = m.getSavePath() != null ? m.getSavePath() : Paths.get(n.getPath() + ".dat");
                File file = OEPaths.getWorldSaveDataPath(server).resolve(n.getNamespace()).resolve(filePat).toFile();
                file.toPath().getParent().toFile().mkdirs();
                m.save(file);
            } catch (Exception e) {
                LOGGER.error("World save data save failure: [" + n + "]", e);
            }
        });
    }

    public void load(MinecraftServer server) {
        SAVE_DATA.forEach((n, m) -> {
            try {
                m.clear();
                Path filePat = m.getSavePath() != null ? m.getSavePath() : Paths.get(n.getPath() + ".dat");
                File file = OEPaths.getWorldSaveDataPath(server).resolve(n.getNamespace()).resolve(filePat).toFile();
                if (file.exists()) {
                    m.load(NbtIo.readCompressed(file).getCompound("data"));
                }
            } catch (IOException e) {
                LOGGER.error("World save data load failure: [" + n + "]", e);
            }
        });
    }

    public void unload() {
        SAVE_DATA.forEach((n, m) -> m.clear());
    }
}
