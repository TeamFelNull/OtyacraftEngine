package dev.felnull.otyacraftengine.impl.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.NativeImage;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;

public class OEClientExpectPlatform {
    @ExpectPlatform
    public static void setNonClosePixels(DynamicTexture texture, NativeImage image) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static InputConstants.Key getKey(KeyMapping key) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void freeTexture(ResourceLocation location) {
        throw new AssertionError();
    }
}
