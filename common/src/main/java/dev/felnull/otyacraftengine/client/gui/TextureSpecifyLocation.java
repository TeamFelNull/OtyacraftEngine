package dev.felnull.otyacraftengine.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record TextureSpecifyLocation(@NotNull ResourceLocation location, int x, int y, int width, int height,
                                     int sizeWidth, int sizeHeight) {
    public TextureSpecifyLocation(@NotNull ResourceLocation location, int x, int y, int width, int height) {
        this(location, x, y, width, height, 256, 256);
    }

    public void draw(@NotNull PoseStack poseStack, float stX, float stY) {
        OERenderUtil.drawTexture(poseStack, stX, stY, this);
    }
}
