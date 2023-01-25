package dev.felnull.otyacraftenginetest.handler;

import dev.architectury.event.EventResult;
import dev.felnull.otyacraftengine.event.MoreEntityEvent;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class CommonHandler {
    public static void init() {
        MoreEntityEvent.LIVING_ENTITY_TICK.register(CommonHandler::livingEntityTick);
    }

    private static EventResult livingEntityTick(@NotNull LivingEntity livingEntity) {

       /* System.out.println(livingEntity);

        if (livingEntity instanceof Villager)
            return EventResult.interruptFalse();*/

        return EventResult.pass();
    }
}
