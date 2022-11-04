package dev.felnull.otyacraftenginetest.client.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.client.util.OEClientUtils;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class TestCode4Screen extends Screen {
    protected TestCode4Screen() {
        super(Component.literal("Test code4"));
    }

    @Override
    protected void init() {
        super.init();

        addRenderableWidget(new Button(0, 0, 200, 20, Component.literal("File Open"), button -> {
            OEClientUtils.openFileChooser("fcoh", null, null, false);
        }));
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int i, int j, float f) {
        this.renderBackground(poseStack);

        super.render(poseStack, i, j, f);
    }
}
