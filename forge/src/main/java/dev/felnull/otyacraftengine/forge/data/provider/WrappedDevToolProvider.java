package dev.felnull.otyacraftengine.forge.data.provider;

import dev.felnull.otyacraftengine.data.provider.DevToolProviderWrapper;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;

import java.io.IOException;

public class WrappedDevToolProvider implements DataProvider {
    private final DevToolProviderWrapper devToolProviderWrapper;

    public WrappedDevToolProvider(DevToolProviderWrapper devToolProviderWrapper) {
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
