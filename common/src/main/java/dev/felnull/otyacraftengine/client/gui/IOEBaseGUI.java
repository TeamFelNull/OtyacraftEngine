package dev.felnull.otyacraftengine.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;

public interface IOEBaseGUI {
    Minecraft mc = Minecraft.getInstance();

    default void drawTextBase(PoseStack poseStack, Component text, int x, int y, int color) {
        GuiComponent.drawString(poseStack, mc.font, text, x, y, color);
    }
}
