package dev.felnull.otyacraftengine.client.event;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.architectury.event.EventResult;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;

public interface OBJLoaderEvent {
    Event<LoadCheck> LOAD_CHECK = EventFactory.createEventResult();

    @Environment(EnvType.CLIENT)
    interface LoadCheck {
        EventResult loadCheck(ResourceLocation location);
    }
}
