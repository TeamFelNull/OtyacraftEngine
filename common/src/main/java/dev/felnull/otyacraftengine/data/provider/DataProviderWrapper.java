package dev.felnull.otyacraftengine.data.provider;

import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import net.minecraft.data.DataProvider;

public abstract class DataProviderWrapper<T extends DataProvider> {
    private final CrossDataGeneratorAccess crossDataGeneratorAccess;

    public DataProviderWrapper(CrossDataGeneratorAccess crossDataGeneratorAccess) {
        this.crossDataGeneratorAccess = crossDataGeneratorAccess;
    }

    public CrossDataGeneratorAccess getCrossDataGeneratorAccess() {
        return crossDataGeneratorAccess;
    }

    public abstract T getProvider();
}
