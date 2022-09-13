package dev.felnull.otyacraftengine.fabric.data;

import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import org.jetbrains.annotations.NotNull;

public interface CrossDataGeneratorAccesses {
    @NotNull
    static CrossDataGeneratorAccess create(FabricDataGenerator fabricDataGenerator) {
        return new CrossDataGeneratorAccessImpl(fabricDataGenerator);
    }
}
