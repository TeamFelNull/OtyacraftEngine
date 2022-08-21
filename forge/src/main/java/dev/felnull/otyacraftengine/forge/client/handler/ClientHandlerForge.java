package dev.felnull.otyacraftengine.forge.client.handler;

import dev.felnull.otyacraftengine.client.event.OEClientEventHooks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientHandlerForge {
    private static final Minecraft mc = Minecraft.getInstance();

    @SubscribeEvent
    public static void onLevelUnload(LevelEvent.Unload e) {
        if (e.getLevel().isClientSide() && e.getLevel() instanceof ClientLevel clientLevel)
            OEClientEventHooks.onLevelUnload(clientLevel);
    }

    @SubscribeEvent
    public static void onClickInput(InputEvent.InteractionKeyMappingTriggered e) {
        if (mc.player == null) return;
        if (e.getKeyMapping() == mc.options.keyAttack && !OEClientEventHooks.onHandAttack(mc.player.getItemInHand(e.getHand()))) {
            e.setCanceled(true);
            e.setSwingHand(false);
        }
    }
}
