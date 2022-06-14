package dev.felnull.otyacraftengine.forge.client.handler;

import dev.felnull.otyacraftengine.client.shape.ClientIVShapeManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientBusHandler {
    @SubscribeEvent
    public static void onRegisterReloadListeners(RegisterClientReloadListenersEvent e) {
        e.registerReloadListener(new ClientIVShapeManager());
    }
}
