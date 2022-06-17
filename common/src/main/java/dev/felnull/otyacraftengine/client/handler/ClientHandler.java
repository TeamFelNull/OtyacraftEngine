package dev.felnull.otyacraftengine.client.handler;

import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.felnull.otyacraftengine.client.event.MoreClientLifecycleEvents;
import dev.felnull.otyacraftengine.client.renderer.texture.URLTextureManager;
import dev.felnull.otyacraftengine.debug.ProcessTimeMeasure;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;

public class ClientHandler {

    public static void init() {
        ClientLifecycleEvent.CLIENT_STOPPING.register(ClientHandler::onStopping);
        ClientLifecycleEvent.CLIENT_LEVEL_LOAD.register(ClientHandler::onLevelLoad);
        MoreClientLifecycleEvents.CLIENT_LEVEL_UNLOAD.register(ClientHandler::onLevelUnload);
        ClientTickEvent.CLIENT_POST.register(ClientHandler::ontClientTick);
    }

    private static void onStopping(Minecraft minecraft) {
        URLTextureManager.getInstance().saveIndex();
    }

    private static void onLevelLoad(ClientLevel world) {
        URLTextureManager.getInstance().clear();
    }

    private static void onLevelUnload(ClientLevel world) {
        URLTextureManager.getInstance().clear();
    }

    private static void ontClientTick(Minecraft instance) {
        URLTextureManager.getInstance().tick();
    }
}
