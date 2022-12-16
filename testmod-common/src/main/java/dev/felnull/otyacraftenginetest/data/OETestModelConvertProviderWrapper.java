package dev.felnull.otyacraftenginetest.data;

import com.google.common.collect.ImmutableList;
import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import dev.felnull.otyacraftengine.data.provider.ModelProcessProviderWrapper;
import dev.felnull.otyacraftengine.data.provider.model.DivisionModelProcessSubProviderWrapper;
import net.minecraft.data.PackOutput;

import java.nio.file.Path;

public class OETestModelConvertProviderWrapper extends ModelProcessProviderWrapper {
    public OETestModelConvertProviderWrapper(PackOutput packOutput, CrossDataGeneratorAccess crossDataGeneratorAccess) {
        super(packOutput, crossDataGeneratorAccess, ImmutableList.of(new DivisionModelProcessSubProviderWrapper(crossDataGeneratorAccess)));
    }

    @Override
    protected boolean isTarget(Path rootPath, Path targetPath) {
        return true;
    }
}
