/*
package dev.felnull.otyacraftenginetest.client.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;


public class TestSelectScreen extends Screen {
    public TestSelectScreen() {
        super(Component.literal("Test Select Screen"));
    }

    @Override
    protected void init() {
        this.addRenderableWidget(createButton(3, 3, 40, 20, Component.literal("Test"), (buttonx) -> {
            minecraft.setScreen(new TestScreen());
        }));
        this.addRenderableWidget(createButton(3, 26, 40, 20, Component.literal("Test1"), (buttonx) -> {
            minecraft.setScreen(new TestCode1Screen());
        }));

        this.addRenderableWidget(createButton(3, 49, 40, 20, Component.literal("Test2"), (buttonx) -> {
            minecraft.setScreen(new TestCode2Screen());
        }));

        this.addRenderableWidget(createButton(3, 72, 40, 20, Component.literal("Test3"), (buttonx) -> {
            minecraft.setScreen(new TestCode3Screen());
        }));

        this.addRenderableWidget(createButton(3, 95, 40, 20, Component.literal("Test4"), (buttonx) -> {
            minecraft.setScreen(new TestCode4Screen());
        }));
    }

    private Button createButton(int x, int y, int width, int height, Component message, Button.OnPress onPress) {
        return Button.builder(message, onPress).bounds(x, y, width, height).build();
    }

    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {
        this.renderBackground(poseStack);
        super.render(poseStack, i, j, f);
    }
}

*/
