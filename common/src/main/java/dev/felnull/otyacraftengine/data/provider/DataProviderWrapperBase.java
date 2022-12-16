package dev.felnull.otyacraftengine.data.provider;

import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;

public interface DataProviderWrapperBase {
    CrossDataGeneratorAccess getCrossGeneratorAccess();

    default ResourceLocation modLoc(String id) {
        return new ResourceLocation(getCrossGeneratorAccess().getMod().getModId(), id);
    }

    default DataGenerator getGenerator() {
        return getCrossGeneratorAccess().getVanillaGenerator();
    }
}
