package dev.felnull.otyacraftengine.handler;

import dev.architectury.event.EventResult;
import dev.felnull.otyacraftengine.event.MoreEntityEvent;
import net.minecraft.world.entity.Entity;

public class TestCommonHandler {
    public static void init() {
        MoreEntityEvent.ENTITY_TICK.register(TestCommonHandler::entityTick);
    }

    private static EventResult entityTick(Entity entity) {
        //   if (!(entity instanceof Player))
        //   return EventResult.interruptFalse();
        //entity.setPos(entity.position().add(0, 0.5f, 0));
        return EventResult.pass();
    }
}
