package red.felnull.otyacraftengine.registries;

import red.felnull.otyacraftengine.OtyacraftEngine;

import java.util.HashMap;
import java.util.Map;

public class OERegistries {
    public static Map<String, Integer> MOD_COLOR = new HashMap<String, Integer>();

    public static void init() {
        registrierModColor("minecraft", 43520);
        registrierModColor("forge", 170);
        registrierModColor(OtyacraftEngine.MODID, 5635925);
        
    }

    public static void registrierModColor(String modid, int color) {
        OERegistries.MOD_COLOR.put(modid, color);
    }


}
