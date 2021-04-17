package red.felnull.otyacraftengine.handler;

import red.felnull.otyacraftengine.api.event.WorldEvent;
import red.felnull.otyacraftengine.data.WorldDataManager;

public class ServerHandler {
    public static void onLoad(WorldEvent.Load e) {
        if (!e.getWorld().isClientSide()) {
            WorldDataManager.getInstance().load(e.getWorld().getServer());
        }
    }

    public static void onUnload(WorldEvent.Unload e) {
        if (!e.getWorld().isClientSide()) {
            WorldDataManager.getInstance().unload();
        }
    }

    public static void onSave(WorldEvent.Save e) {
        if (!e.getWorld().isClientSide()) {
            WorldDataManager.getInstance().save(e.getWorld().getServer());
        }
    }
}