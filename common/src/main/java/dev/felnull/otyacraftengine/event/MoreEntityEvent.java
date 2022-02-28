package dev.felnull.otyacraftengine.event;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.architectury.event.EventResult;
import net.minecraft.world.entity.Entity;

public interface MoreEntityEvent {
    Event<EntityTick> ENTITY_TICK = EventFactory.createEventResult();

    public interface EntityTick {
        EventResult entityTick(Entity entity);
    }
}
