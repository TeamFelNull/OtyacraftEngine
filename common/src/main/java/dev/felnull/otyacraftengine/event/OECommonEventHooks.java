package dev.felnull.otyacraftengine.event;

import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class OECommonEventHooks {
    public static boolean onEntityTick(@NotNull Entity entity) {
        var event = MoreEntityEvent.ENTITY_TICK.invoker().entityTick(entity);
        return event.isEmpty() || event.isTrue();
    }
}