package dev.felnull.otyacraftengine.client.event;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;

public class OEClientEventHooks {
    public static void onLevelUnload(ClientLevel level) {
        MoreClientLifecycleEvents.CLIENT_LEVEL_UNLOAD.invoker().act(level);
    }

    public static boolean onOBJLoaderCheck(ResourceLocation resourceId) {
        return OBJLoaderEvent.LOAD_CHECK.invoker().loadCheck(resourceId).isTrue();
    }
}
