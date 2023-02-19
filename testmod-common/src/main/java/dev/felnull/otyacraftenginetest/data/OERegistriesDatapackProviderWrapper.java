package dev.felnull.otyacraftenginetest.data;

import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import dev.felnull.otyacraftengine.data.provider.RegistriesDatapackProviderWrapper;
import dev.felnull.otyacraftenginetest.server.level.OETestDimensions;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public class OERegistriesDatapackProviderWrapper extends RegistriesDatapackProviderWrapper {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.DIMENSION_TYPE, OETestDimensions::bootstrapType)
            .add(Registries.LEVEL_STEM, OETestDimensions::bootstrapStem)
            .add(Registries.NOISE_SETTINGS, OETestDimensions::bootstrapNoiseGeneratorSettings);

    public OERegistriesDatapackProviderWrapper(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookup, CrossDataGeneratorAccess crossDataGeneratorAccess) {
        super(packOutput, lookup, BUILDER, crossDataGeneratorAccess);
    }
}
