package red.felnull.otyacraftengine.api.registries;

import net.minecraft.util.ResourceLocation;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.data.PlayerWorldData;
import red.felnull.otyacraftengine.data.WorldData;

import java.util.HashMap;
import java.util.Map;

public class OERegistries {
    public static Map<String, Integer> MOD_COLOR = new HashMap<String, Integer>();
    public static Map<ResourceLocation, PlayerWorldData> PLAYER_WORLD_DATA = new HashMap<ResourceLocation, PlayerWorldData>();
    public static Map<ResourceLocation, WorldData> WORLD_DATA = new HashMap<ResourceLocation, WorldData>();
    public static Map<ResourceLocation, String> SERVER_RECEVED_PATH = new HashMap<ResourceLocation, String>();
    public static Map<ResourceLocation, String> CLIENT_RECEVED_PATH = new HashMap<ResourceLocation, String>();
    public static Map<ResourceLocation, String> TEXTUER_SEND_PATH = new HashMap<ResourceLocation, String>();


    public static void init() {
        registrierModColor("minecraft", 43520);
        registrierModColor("forge", 170);
        registrierModColor(OtyacraftEngine.MODID, 5635925);
        registrierClientRecevedPath(new ResourceLocation(OtyacraftEngine.MODID, "textuerrequest"), "receivetextures/cash");

        registrierTextuerSendPath(new ResourceLocation(OtyacraftEngine.MODID, "test"), "test/ikisygi");


        //  registrierPlayerData(new ResourceLocation(OtyacraftEngine.MODID, "test"), new TestPlayerWorldData());
        // registrierWorldData(new ResourceLocation(OtyacraftEngine.MODID, "test"), new TestWorldData());
        //  registrierClientRecevedPath(new ResourceLocation(OtyacraftEngine.MODID, "test"), OtyacraftEngine.MODID + "/testrecikuyo");
    }

    public static void registrierModColor(String modid, int color) {
        OERegistries.MOD_COLOR.put(modid, color);
    }

    public static void registrierPlayerWorldData(ResourceLocation location, PlayerWorldData data) {
        OERegistries.PLAYER_WORLD_DATA.put(location, data);
    }

    public static void registrierWorldData(ResourceLocation location, WorldData data) {
        OERegistries.WORLD_DATA.put(location, data);
    }

    public static void registrierServerRecevedPath(ResourceLocation location, String path) {
        OERegistries.SERVER_RECEVED_PATH.put(location, path);
    }

    public static void registrierClientRecevedPath(ResourceLocation location, String path) {
        OERegistries.CLIENT_RECEVED_PATH.put(location, path);
    }

    public static void registrierTextuerSendPath(ResourceLocation location, String path) {
        OERegistries.TEXTUER_SEND_PATH.put(location, path);
    }
}
