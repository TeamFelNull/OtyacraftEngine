package dev.felnull.otyacraftengine.event;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.architectury.event.EventResult;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public interface MoreEntityEvent {
    Event<EntityTick> ENTITY_TICK = EventFactory.createEventResult();
    Event<EntityDefineSynchedData> ENTITY_DEFINE_SYNCHED_DATA = EventFactory.createLoop();

    public interface EntityTick {
        EventResult entityTick(@NotNull Entity entity);
    }

    public interface EntityDefineSynchedData {
        void onDefineSynchedData(@NotNull Entity entity, @NotNull SynchedEntityData entityData);
    }
}
