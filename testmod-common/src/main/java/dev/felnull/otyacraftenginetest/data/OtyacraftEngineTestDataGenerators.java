package dev.felnull.otyacraftenginetest.data;

import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;

public class OtyacraftEngineTestDataGenerators {
    public static void init(CrossDataGeneratorAccess access) {
        access.addProvider(new OETestRecipeProvider(access));
        access.addProvider(new OETestItemTagProviderWrapper(access));
    }
}
