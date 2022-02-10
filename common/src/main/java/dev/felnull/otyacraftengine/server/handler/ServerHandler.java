package dev.felnull.otyacraftengine.server.handler;

import dev.architectury.event.events.common.LifecycleEvent;
import dev.felnull.otyacraftengine.api.event.common.MoreLifecycleEvent;
import dev.felnull.otyacraftengine.server.data.WorldDataManager;
import net.minecraft.server.MinecraftServer;

public class ServerHandler {
    public static void init() {
        LifecycleEvent.SERVER_STARTING.register(ServerHandler::onServerStarting);
        LifecycleEvent.SERVER_STOPPED.register(ServerHandler::onServerStopped);
        MoreLifecycleEvent.SERVER_SAVING.register(ServerHandler::onServerSave);
    }

    private static void onServerStarting(MinecraftServer server) {
        WorldDataManager.getInstance().load(server);
    }

    private static void onServerStopped(MinecraftServer server) {
        WorldDataManager.getInstance().unload();
    }

    private static void onServerSave(MinecraftServer server) {
        WorldDataManager.getInstance().save(server);
    }
}
