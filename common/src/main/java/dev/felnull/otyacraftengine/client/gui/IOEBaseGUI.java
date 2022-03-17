package dev.felnull.otyacraftengine.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public interface IOEBaseGUI {
    Minecraft mc = Minecraft.getInstance();

    default void drawTextBase(@NotNull PoseStack poseStack, @NotNull Component text, int x, int y, int color) {
        GuiComponent.drawString(poseStack, mc.font, text, x, y, color);
    }

    default void drawTextBase(@NotNull PoseStack poseStack, @NotNull String text, int x, int y, int color) {
        GuiComponent.drawString(poseStack, mc.font, text, x, y, color);
    }

    default void drawTextBase(@NotNull PoseStack poseStack, @NotNull Component text, float x, float y, int color) {
        mc.font.drawShadow(poseStack, text, x, y, color);
    }

    default void drawTextBase(@NotNull PoseStack poseStack, @NotNull String text, float x, float y, int color) {
        mc.font.drawShadow(poseStack, text, x, y, color);
    }

    default void drawRelativeFill(@NotNull PoseStack poseStack, int x, int y, int w, int h, int col) {
        GuiComponent.fill(poseStack, x, y, x + w, y + h, col);
    }

    default void drawRelativeFill(@NotNull PoseStack poseStack, float x, float y, float w, float h, int col) {
        OERenderUtil.drawFill(poseStack, x, y, x + w, y + h, col);
    }
}
