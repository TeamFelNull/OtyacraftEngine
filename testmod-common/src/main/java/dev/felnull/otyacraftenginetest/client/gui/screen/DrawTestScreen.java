package dev.felnull.otyacraftenginetest.client.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.client.gui.screen.OEBasedScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class DrawTestScreen extends OEBasedScreen {

    public DrawTestScreen(Screen parentScreen) {
        super(Component.literal("Draw Test"), parentScreen);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mx, int my, float delta) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mx, my, delta);

        int rainbow = (int) (((float) System.currentTimeMillis() / 100f) % 0xFFFFFF);
        drawTextBase(guiGraphics, "The Ikisugi Text...", 3, 3, 0xFF000000 | rainbow);
    }
}
