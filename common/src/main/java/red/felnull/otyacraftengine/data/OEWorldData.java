package red.felnull.otyacraftengine.data;

import net.minecraft.resources.ResourceLocation;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.api.OtyacraftEngineAPI;

public class OEWorldData {
    public static void init() {
        if (OtyacraftEngineAPI.getInstance().isTestMode()) {
            register("test", new TestSaveData());
        }
    }

    public static void register(String name, IkisugiSaveData data) {
        WorldDataManager.getInstance().register(new ResourceLocation(OtyacraftEngine.MODID, name), data);
    }
}
