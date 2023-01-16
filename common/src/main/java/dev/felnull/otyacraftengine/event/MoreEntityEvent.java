package dev.felnull.otyacraftengine.event;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public interface MoreEntityEvent {
    Event<EntityDefineSynchedData> ENTITY_DEFINE_SYNCHED_DATA = EventFactory.createLoop();

    interface EntityDefineSynchedData {
        void onDefineSynchedData(@NotNull Entity entity, @NotNull SynchedEntityData entityData);
    }
}
