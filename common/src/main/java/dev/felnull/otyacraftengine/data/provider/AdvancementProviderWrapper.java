package dev.felnull.otyacraftengine.data.provider;

import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.RegistriesDatapackGenerator;

import java.util.List;

public class AdvancementProviderWrapper extends DataProviderWrapper<DataProvider> {
    private final DataProvider advancementProvider;

    public AdvancementProviderWrapper(PackOutput packOutput, CrossDataGeneratorAccess crossDataGeneratorAccess, List<AdvancementSubProviderWrapper> subProviderWrappers) {
        super(packOutput, crossDataGeneratorAccess);
        this.advancementProvider = crossDataGeneratorAccess.createAdvancementProvider(packOutput, this, subProviderWrappers);
    }

    @Override
    public DataProvider getProvider() {
        return advancementProvider;
    }
}
