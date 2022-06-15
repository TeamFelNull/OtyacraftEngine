package dev.felnull.otyacraftengine.explatform.client;

import com.mojang.blaze3d.platform.InputConstants;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.felnull.otyacraftengine.client.shape.ClientIVShapeManager;
import net.minecraft.client.KeyMapping;

public class OEClientExpectPlatform {
    @ExpectPlatform
    public static InputConstants.Key getKey(KeyMapping key) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ClientIVShapeManager createCIVSManagerInstance() {
        throw new AssertionError();
    }
}
