package dev.felnull.otyacraftengine.fabric.data.provider;

import dev.felnull.otyacraftengine.data.provider.DevToolProviderWrapper;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;

import java.io.IOException;

public class WrappedFabricDevToolProvider implements DataProvider {
    private final DevToolProviderWrapper devToolProviderWrapper;

    public WrappedFabricDevToolProvider(DevToolProviderWrapper devToolProviderWrapper) {
        this.devToolProviderWrapper = devToolProviderWrapper;
    }

    @Override
    public void run(CachedOutput cachedOutput) throws IOException {
        devToolProviderWrapper.run(cachedOutput);
    }

    @Override
    public String getName() {
        return devToolProviderWrapper.getName();
    }
}
