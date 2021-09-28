package dev.felnull.otyacraftengine.impl.client.forge;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.renderer.texture.DynamicTexture;

public class OEClientExpectPlatformImpl {
    public static void setNonClosePixels(DynamicTexture texture, NativeImage image) {

    }

    public static InputConstants.Key getKey(KeyMapping key) {
        return key.getKey();
    }
}
