package red.felnull.otyacraftengine.data;

import net.minecraft.resources.ResourceLocation;
import red.felnull.otyacraftengine.OtyacraftEngine;

public class OEWorldData {
    public static void init() {
        register("test", new TestSaveData());
    }

    public static void register(String name, IkisugiSaveData data) {
        WorldDataManager.getInstance().register(new ResourceLocation(OtyacraftEngine.MODID, name), data);
    }
}
