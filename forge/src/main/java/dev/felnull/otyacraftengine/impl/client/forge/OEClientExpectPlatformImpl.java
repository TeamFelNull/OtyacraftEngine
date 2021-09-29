package dev.felnull.otyacraftengine.impl.client.forge;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;

public class OEClientExpectPlatformImpl {
    private static final Minecraft mc = Minecraft.getInstance();

    public static void setNonClosePixels(DynamicTexture texture, NativeImage image) {
        texture.pixels = image;
    }

    public static InputConstants.Key getKey(KeyMapping key) {
        return key.getKey();
    }

    public static void freeTexture(ResourceLocation location) {
        TextureManager textureManager = mc.getTextureManager();
        AbstractTexture abstractTexture = textureManager.byPath.get(location);
        if (abstractTexture != null) {
            textureManager.tickableTextures.remove(abstractTexture);
            textureManager.safeClose(location, abstractTexture);
        }
    }
}
