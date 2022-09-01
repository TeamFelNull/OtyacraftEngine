package dev.felnull.otyacraftengine.explatform.fabric;

import net.fabricmc.fabric.impl.datagen.FabricDataGenHelper;

public class OEDataGenExpectPlatformImpl {
    public static boolean isDataGenerating() {
        return FabricDataGenHelper.ENABLED;
    }
}
