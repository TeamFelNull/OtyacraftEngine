package dev.felnull.otyacraftengine.client.event;

import net.minecraft.client.multiplayer.ClientLevel;

public class OEClientEventHooks {
    public static void onLevelUnload(ClientLevel level) {
        MoreClientLifecycleEvents.CLIENT_LEVEL_UNLOAD.invoker().act(level);
    }
}
