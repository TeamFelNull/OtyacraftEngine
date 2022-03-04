package dev.felnull.otyacraftengine.client.gui.components.base;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.OtyacraftEngine;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public interface IOEBaseComponent {
    Minecraft mc = Minecraft.getInstance();
    ResourceLocation WIDGETS = new ResourceLocation(OtyacraftEngine.MODID, "textures/gui/widgets.png");

    default void drawTextBase(PoseStack poseStack, Component text, int x, int y, int color) {
        GuiComponent.drawString(poseStack, mc.font, text, x, y, color);
    }
}
