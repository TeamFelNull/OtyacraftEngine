package dev.felnull.otyacraftengine.item.location.factory;

import dev.felnull.otyacraftengine.item.location.PlayerItemLocation;
import dev.felnull.otyacraftengine.item.location.PlayerItemLocations;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

public interface PlayerItemLocationFactory<T extends PlayerItemLocation> {
    T create(CompoundTag tag);

    default ResourceLocation getLocation() {
        return PlayerItemLocations.getResourceLocationByFactory(this);
    }
}
