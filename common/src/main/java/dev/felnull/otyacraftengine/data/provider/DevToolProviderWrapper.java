package dev.felnull.otyacraftengine.data.provider;

import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import dev.felnull.otyacraftengine.data.DataGeneratorType;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;

import java.io.IOException;

public abstract class DevToolProviderWrapper extends DataProviderWrapper<DataProvider> {
    private final DataProvider devToolProvider;

    public DevToolProviderWrapper(CrossDataGeneratorAccess crossDataGeneratorAccess) {
        super(crossDataGeneratorAccess);
        this.devToolProvider = crossDataGeneratorAccess.createDevToolProvider(this);
    }

    @Override
    public DataProvider getProvider() {
        return devToolProvider;
    }

    @Override
    public DataGeneratorType getGeneratorType() {
        return DataGeneratorType.DEV;
    }

    public abstract void run(CachedOutput cachedOutput) throws IOException;

    public abstract String getName();
}
