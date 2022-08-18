package dev.felnull.otyacraftengine.forge.client.handler;

import dev.felnull.otyacraftengine.client.event.OEClientEventHooks;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientHandlerForge {
    @SubscribeEvent
    public static void onLevelUnload(LevelEvent.Unload e) {
        if (e.getLevel().isClientSide() && e.getLevel() instanceof ClientLevel clientLevel)
            OEClientEventHooks.onLevelUnload(clientLevel);
    }
}
