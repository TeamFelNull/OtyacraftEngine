package dev.felnull.otyacraftengine.fabric.client.model;

import com.google.gson.JsonElement;
import dev.felnull.otyacraftengine.fabric.client.model.impl.OBJOptionImpl;

import java.util.Optional;
import java.util.function.Function;

public interface OBJOption {
    static OBJOption of(boolean flipV, boolean ambientToFullbright) {
        return new OBJOptionImpl(flipV, ambientToFullbright);
    }

    static OBJOption of(Function<String, Optional<JsonElement>> elementFunction) {
        return OBJOptionImpl.of(elementFunction);
    }

    boolean isFlipV();

    boolean isAmbientToFullbright();
}
