package dev.felnull.otyacraftengine.server.data;

import dev.felnull.otyacraftengine.OtyacraftEngine;
import net.minecraft.resources.ResourceLocation;

public class OEWorldData {
    public static void init() {
        if (OtyacraftEngine.isTestMode()) {
            register("test", new TestSaveData());
        }
    }

    private static void register(String name, OEBaseSaveData data) {
        WorldDataManager.getInstance().register(new ResourceLocation(OtyacraftEngine.MODID, name), data);
    }
}
