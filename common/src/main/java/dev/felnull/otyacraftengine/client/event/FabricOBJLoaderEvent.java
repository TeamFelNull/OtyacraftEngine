package dev.felnull.otyacraftengine.client.event;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.architectury.event.EventResult;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;

public interface FabricOBJLoaderEvent {
    Event<FabricOBJLoaderEvent.Load> LOAD = EventFactory.createEventResult();

    @Environment(EnvType.CLIENT)
    public interface Load {
        EventResult load(ResourceLocation location);
    }
}
