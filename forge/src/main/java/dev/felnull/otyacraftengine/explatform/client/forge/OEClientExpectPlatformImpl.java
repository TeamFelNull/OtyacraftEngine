package dev.felnull.otyacraftengine.explatform.client.forge;

import com.mojang.blaze3d.platform.InputConstants;
import dev.felnull.otyacraftengine.client.shape.ClientIVShapeManager;
import net.minecraft.client.KeyMapping;

public class OEClientExpectPlatformImpl {
    public static InputConstants.Key getKey(KeyMapping key) {
        return key.getKey();
    }

    public static ClientIVShapeManager createCIVSManagerInstance() {
        return new ClientIVShapeManager();
    }
}
