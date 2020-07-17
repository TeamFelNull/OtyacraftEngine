package red.felnull.otyacraftengine.handler;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import red.felnull.otyacraftengine.api.event.WorldDataEvent;
import red.felnull.otyacraftengine.data.WorldDataManager;

public class WorldDataHandler {
    @SubscribeEvent
    public static void onLoad(WorldDataEvent.Load e) {
        if (e.isThread()) {
            DataThread dt = new DataThread(0, e.getServer(), e.getPlayer());
            dt.start();
        } else {
            WorldDataManager.instance().load(e.getServer(), e.getPlayer());
        }
    }

    @SubscribeEvent
    public static void onSave(WorldDataEvent.Save e) {
        if (e.isThread()) {
            DataThread dt = new DataThread(1, e.getServer(), e.getPlayer());
            dt.start();
        } else {
            WorldDataManager.instance().save(e.getServer(), e.getPlayer());
        }
    }

    @SubscribeEvent
    public static void onUnLoad(WorldDataEvent.UnLoad e) {
        if (e.isThread()) {
            DataThread dt = new DataThread(2, e.getServer(), e.getPlayer());
            dt.start();
        } else {
            WorldDataManager.instance().unload(e.getServer(), e.getPlayer());
        }
    }


    private static class DataThread extends Thread {
        private int num;
        private MinecraftServer ms;
        private ServerPlayerEntity player;

        public DataThread(int num, MinecraftServer ms, ServerPlayerEntity playerEntity) {
            this.num = num;
            this.ms = ms;
            this.player = playerEntity;
        }

        public void run() {
            if (num == 0) {
                WorldDataManager.instance().load(ms, player);
            } else if (num == 1) {
                WorldDataManager.instance().save(ms, player);
            } else if (num == 2) {
                WorldDataManager.instance().unload(ms, player);
            }
        }
    }
}

