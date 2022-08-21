package dev.felnull.otyacraftengine.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.client.util.OERenderUtils;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Internal
public record TextureSpecifyImpl(@NotNull ResourceLocation location, float u0, float v0, float u1, float v1,
                                 float textureWidth, float textureHeight, boolean alpha) implements TextureSpecify {
    @Override
    public void draw(@NotNull PoseStack poseStack, float x, float y) {
        if (alpha) {
            OERenderUtils.drawTextureAlpha(location, poseStack, x, y, u0, v0, u1, v1, textureWidth, textureHeight);
        } else {
            OERenderUtils.drawTexture(location, poseStack, x, y, u0, v0, u1, v1, textureWidth, textureHeight);
        }
    }

    @Override
    public void draw(@NotNull PoseStack poseStack, float x, float y, float width, float height) {
        float scX = width / (u1 - u0);
        float scY = height / (v1 - v0);

        if (alpha) {
            OERenderUtils.drawTextureAlpha(location, poseStack, x, y, u0 * scX, v0 * scY, u1 * scX, v1 * scY, textureWidth * scX, textureHeight * scY);
        } else {
            OERenderUtils.drawTexture(location, poseStack, x, y, u0 * scX, v0 * scY, u1 * scX, v1 * scY, textureWidth * scX, textureHeight * scY);
        }
    }

    @Override
    public float getWidth() {
        return u1 - u0;
    }

    @Override
    public float getHeight() {
        return v1 - v0;
    }

    @Override
    public float getU0() {
        return u0;
    }

    @Override
    public float getV0() {
        return v0;
    }

    @Override
    public float getU1() {
        return u1;
    }

    @Override
    public float getV1() {
        return v1;
    }

    @Override
    public ResourceLocation getTextureLocation() {
        return location;
    }

    @Override
    public float getTextureWidth() {
        return textureWidth;
    }

    @Override
    public float getTextureHeight() {
        return textureHeight;
    }
}