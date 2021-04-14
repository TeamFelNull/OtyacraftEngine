package red.felnull.otyacraftengine.data;


import net.minecraft.resources.ResourceLocation;
import red.felnull.otyacraftengine.util.IKSGPathUtil;

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

    public void save() {
        SAVE_DATAS.forEach((n, m) -> {
            m.save(IKSGPathUtil.getWorldSaveDataPath().resolve(n.getNamespace()).resolve(m.getSavePath()).toFile());
        });
    }

}
