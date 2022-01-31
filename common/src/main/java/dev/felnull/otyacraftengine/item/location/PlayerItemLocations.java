package dev.felnull.otyacraftengine.item.location;

import dev.felnull.otyacraftengine.OtyacraftEngine;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class PlayerItemLocations {
    private static final Map<ResourceLocation, Function<CompoundTag, IPlayerItemLocation>> FACTORYS = new HashMap<>();
    public static final ResourceLocation HAND = new ResourceLocation(OtyacraftEngine.MODID, "hand");

    public static void init() {
        register(HAND, HandItemLocation::factory);
    }

    private static void register(ResourceLocation location, Function<CompoundTag, IPlayerItemLocation> factory) {
        FACTORYS.put(location, factory);
    }

    public static IPlayerItemLocation create(ResourceLocation location, CompoundTag tag) {
        var f = FACTORYS.get(location);
        if (f != null)
            return f.apply(tag);
        return null;
    }
}
