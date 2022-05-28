package dev.felnull.otyacraftengine.client.util;

import dev.felnull.otyacraftengine.impl.client.OEClientExpectPlatform;
import net.minecraft.resources.ResourceLocation;

public class OEShaderUtil {
    public static void loadShader(ResourceLocation location) {
        OEClientExpectPlatform.loadShader(location);
    }
}
