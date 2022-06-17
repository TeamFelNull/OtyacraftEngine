package dev.felnull.otyacraftengine.forge.client.handler;

import dev.felnull.otyacraftengine.client.event.OEClientEventHooks;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientHandlerForge {
    @SubscribeEvent
    public static void onWorldUnload(WorldEvent.Unload e) {
        if (e.getWorld().isClientSide() && e.getWorld() instanceof ClientLevel clientLevel)
            OEClientEventHooks.onLevelUnload(clientLevel);
    }
}
