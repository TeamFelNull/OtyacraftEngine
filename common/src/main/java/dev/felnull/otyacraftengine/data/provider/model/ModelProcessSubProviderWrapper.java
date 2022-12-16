package dev.felnull.otyacraftengine.data.provider.model;

import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import dev.felnull.otyacraftengine.data.provider.DataProviderWrapperBase;
import dev.felnull.otyacraftengine.data.provider.ModelProcessProviderWrapper;
import net.minecraft.data.CachedOutput;

import java.util.List;

public abstract class ModelProcessSubProviderWrapper implements DataProviderWrapperBase {
    protected final CrossDataGeneratorAccess crossDataGeneratorAccess;

    public ModelProcessSubProviderWrapper(CrossDataGeneratorAccess crossDataGeneratorAccess) {
        this.crossDataGeneratorAccess = crossDataGeneratorAccess;
    }

    @Override
    public CrossDataGeneratorAccess getCrossGeneratorAccess() {
        return crossDataGeneratorAccess;
    }

    public List<ModelProcessProviderWrapper.ModelData> process(CachedOutput cachedOutput, List<ModelProcessProviderWrapper.ModelData> modelData) {
        return modelData.stream().flatMap(r -> process(cachedOutput, r).stream()).toList();
    }

    public abstract List<ModelProcessProviderWrapper.ModelData> process(CachedOutput cachedOutput, ModelProcessProviderWrapper.ModelData modelData);
}
