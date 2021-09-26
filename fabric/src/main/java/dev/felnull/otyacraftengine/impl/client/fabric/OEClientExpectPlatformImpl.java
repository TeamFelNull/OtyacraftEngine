package dev.felnull.otyacraftengine.impl.client.fabric;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.renderer.texture.DynamicTexture;

public class OEClientExpectPlatformImpl {
    public static void setNonClosePixels(DynamicTexture texture, NativeImage image) {
        texture.pixels = image;
    }
}
