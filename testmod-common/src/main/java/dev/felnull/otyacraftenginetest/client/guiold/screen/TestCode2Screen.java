/*
package dev.felnull.otyacraftenginetest.client.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.client.util.OERenderUtils;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;

public class TestCode2Screen extends Screen {
    private int tick;

    public TestCode2Screen() {
        super(Component.literal("Test code2"));
    }

    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {
        this.renderBackground(poseStack);
        var rnd = RandomSource.create(114514);
        for (int k = 0; k < (tick / 60) + 1; k++) {
            OERenderUtils.drawCenterFont(poseStack, "This is ikisugi test", width / 2f + (float) Math.sin((double) (System.currentTimeMillis() + k * 1000L) * rnd.nextFloat() / 1000d) * width / 4f, height / 2f + (float) Math.sin((double) (System.currentTimeMillis() + k * 1000L) * rnd.nextFloat() / 1300d) * height / 4f, 0xFF000000 | rnd.nextInt(0xFFFFFF));
        }

        super.render(poseStack, i, j, f);
    }

    @Override
    public void tick() {
        tick++;
    }
}
*/
