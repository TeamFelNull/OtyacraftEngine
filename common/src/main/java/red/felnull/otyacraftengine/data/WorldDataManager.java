package red.felnull.otyacraftengine.data;


import net.minecraft.nbt.NbtIo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import red.felnull.otyacraftengine.util.IKSGPathUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WorldDataManager {
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
                File file = IKSGPathUtil.getWorldSaveDataPath(server).resolve(n.getNamespace()).resolve(m.getSavePath()).toFile();
                file.toPath().getParent().toFile().mkdirs();
                m.save(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void load(MinecraftServer server) {
        SAVE_DATAS.forEach((n, m) -> {
            try {
                m.clear();
                File file = IKSGPathUtil.getWorldSaveDataPath(server).resolve(n.getNamespace()).resolve(m.getSavePath()).toFile();
                m.load(NbtIo.readCompressed(file).getCompound("data"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void unload() {
        SAVE_DATAS.forEach((n, m) -> m.clear());
    }

}
