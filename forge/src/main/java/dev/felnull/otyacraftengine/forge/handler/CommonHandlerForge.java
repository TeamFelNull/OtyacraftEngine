package dev.felnull.otyacraftengine.forge.handler;

import dev.felnull.otyacraftengine.event.OECommonEventHooks;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CommonHandlerForge {
    @SubscribeEvent
    public static void onEntityConstructing(EntityEvent.EntityConstructing e) {
        OECommonEventHooks.onEntityDefineSynchedData(e.getEntity(), e.getEntity().getEntityData());
    }
}
