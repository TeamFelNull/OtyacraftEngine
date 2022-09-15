package dev.felnull.otyacraftengine.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public interface TextureSpecify {
    static TextureSpecify create(@NotNull ResourceLocation location, float u0, float v0, float u1, float v1, float textureWidth, float textureHeight, boolean alpha) {
        return new TextureSpecifyImpl(location, u0, v0, u1, v1, textureWidth, textureHeight, alpha);
    }

    static TextureSpecify create(@NotNull ResourceLocation location, float u0, float v0, float u1, float v1, float textureWidth, float textureHeight) {
        return new TextureSpecifyImpl(location, u0, v0, u1, v1, textureWidth, textureHeight, false);
    }

    static TextureSpecify create(@NotNull ResourceLocation location, float u0, float v0, float u1, float v1, boolean alpha) {
        return new TextureSpecifyImpl(location, u0, v0, u1, v1, 256, 256, alpha);
    }

    static TextureSpecify create(@NotNull ResourceLocation location, float u0, float v0, float u1, float v1) {
        return new TextureSpecifyImpl(location, u0, v0, u1, v1, 256, 256, false);
    }

    static TextureSpecify createRelative(@NotNull ResourceLocation location, float u0, float v0, float ru1, float rv1) {
        return new TextureSpecifyImpl(location, u0, v0, u0 + ru1, v0 + rv1, 256, 256, false);
    }

    static TextureSpecify createRelative(@NotNull ResourceLocation location, float u0, float v0, float ru1, float rv1, boolean alpha) {
        return new TextureSpecifyImpl(location, u0, v0, u0 + ru1, v0 + rv1, 256, 256, alpha);
    }

    void draw(@NotNull PoseStack poseStack, float x, float y);

    void draw(@NotNull PoseStack poseStack, float x, float y, float width, float height);

    float getWidth();

    float getHeight();

    float getU0();

    float getV0();

    float getU1();

    float getV1();

    ResourceLocation getTextureLocation();

    float getTextureWidth();

    float getTextureHeight();
}
