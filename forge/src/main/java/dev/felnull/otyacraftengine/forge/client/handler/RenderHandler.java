package dev.felnull.otyacraftengine.forge.client.handler;

import dev.felnull.otyacraftengine.api.event.client.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderHandler {
    @SubscribeEvent
    public static void onRenderHand(RenderHandEvent e) {
        RenderPlayerEvent.RENDER_HAND.invoker().renderHand(e.getMatrixStack(), e.getBuffers(), e.getHand(), e.getLight(), e.getPartialTicks(), e.getInterpolatedPitch(), e.getSwingProgress(), e.getEquipProgress(), e.getItemStack());
    }
}
