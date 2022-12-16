package dev.felnull.otyacraftenginetest.data;

import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import dev.felnull.otyacraftengine.data.provider.DirectCopyProviderWrapper;
import net.minecraft.data.PackOutput;

import java.nio.file.Paths;

public class OtyacraftEngineTestDataGenerators {
    public static void init(CrossDataGeneratorAccess access) {
        access.addResourceInputFolders(Paths.get("../../testmod-resources"));

        access.addProviderWrapper(OETestRecipeProvider::new);
        access.addProviderWrapper(OETestAdvancementProviderWrapper::new);
        access.addProviderWrapper(OETestItemModelProviderWrapper::new);
        var blockTagProvider = access.addProviderWrapper(OETestBlockTagProviderWrapper::new);
        access.addProviderWrapper((packOutput, lookup, generatorAccess) -> new OETestItemTagProviderWrapper(packOutput, lookup, generatorAccess, blockTagProvider));

        access.addProviderWrapper(packOutput -> new DirectCopyProviderWrapper(packOutput, PackOutput.Target.RESOURCE_PACK, "copy_test", access));
        access.addProviderWrapper(OETestModelConvertProviderWrapper::new);
    }
}
