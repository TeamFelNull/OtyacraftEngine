package dev.felnull.otyacraftengine.forge.client.handler;

import dev.felnull.otyacraftengine.client.event.OEClientEventHooks;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientHandler {
    private static final Minecraft mc = Minecraft.getInstance();

    @SubscribeEvent
    public static void onClickInput(InputEvent.ClickInputEvent e) {
        if (mc.player == null) return;
        if (e.getKeyMapping() == Minecraft.getInstance().options.keyAttack && !OEClientEventHooks.onHandAttack(mc.player.getItemInHand(e.getHand()))) {
            e.setCanceled(true);
            e.setSwingHand(false);
        }
    }
}
