package dev.felnull.otyacraftengine.fabric.client.model.impl;

import com.google.gson.JsonElement;
import dev.felnull.otyacraftengine.fabric.client.model.OBJOption;

import java.util.Optional;
import java.util.function.Function;

public record OBJOptionImpl(boolean flipV, boolean ambientToFullbright) implements OBJOption {
    public static OBJOptionImpl of(Function<String, Optional<JsonElement>> elementFunction) {
        boolean flipV = elementFunction.apply("flip_v").map(e -> e.isJsonPrimitive() && e.getAsJsonPrimitive().isBoolean() && e.getAsBoolean()).orElse(false);
        boolean ambientToFullbright = elementFunction.apply("emissive_ambient").map(e -> e.isJsonPrimitive() && e.getAsJsonPrimitive().isBoolean() && e.getAsBoolean()).orElse(true);
        return new OBJOptionImpl(flipV, ambientToFullbright);
    }

    @Override
    public boolean isFlipV() {
        return flipV;
    }

    @Override
    public boolean isAmbientToFullbright() {
        return ambientToFullbright;
    }
}
