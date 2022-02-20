package dev.felnull.otyacraftengine.forge.client.handler;

import dev.felnull.otyacraftengine.client.event.OEClientEventHooks;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderHandler {
    @SubscribeEvent
    public static void onRenderHand(RenderHandEvent e) {
        if (!OEClientEventHooks.onRenderHand(e.getMatrixStack(), e.getBuffers(), e.getHand(), e.getLight(), e.getPartialTicks(), e.getInterpolatedPitch(), e.getSwingProgress(), e.getEquipProgress(), e.getItemStack()))
            e.setCanceled(true);
    }
}
