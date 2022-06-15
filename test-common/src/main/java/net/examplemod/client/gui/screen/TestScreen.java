package net.examplemod.client.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.client.util.OERenderUtils;
import dev.felnull.otyacraftengine.debug.ProcessTimeMeasure;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class TestScreen extends Screen {
    public static final ResourceLocation TEST_TEXTURE = new ResourceLocation("test", "textures/gui/test.png");
    private final ProcessTimeMeasure processTimeMeasure = new ProcessTimeMeasure();

    public TestScreen() {
        super(Component.literal("Test Screen"));
    }

    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {
        this.renderBackground(poseStack);
        super.render(poseStack, i, j, f);

        //  OERenderUtils.drawTexture(TEST_TEXTURE, poseStack, 0, 0, 0, 0, 100, 100, 100, 100);
      /*  OERenderUtils.setPreDraw(TEST_TEXTURE);

        OERenderUtils.floatBlit(poseStack, 0, 0, 0, 0, 100, 100, 100, 100);
        OERenderUtils.floatBlit(poseStack, 150, 0, 0, 0, 100, 100, 100, 100);
        OERenderUtils.floatBlit(poseStack, 300, 0, 0, 0, 100, 100, 100, 100);
        OERenderUtils.floatBlit(poseStack, 0, 150, 0, 0, 100, 100, 100, 100);
        OERenderUtils.floatBlit(poseStack, 0, 300, 0, 0, 100, 100, 100, 100);*/

        //  processTimeMeasure.process(() -> {
           /* OERenderUtils.drawTexture(TEST_TEXTURE, poseStack, 0, 0, 0, 0, 100, 100, 100, 100);
            OERenderUtils.drawTexture(TEST_TEXTURE, poseStack, 150, 0, 0, 0, 100, 100, 100, 100);
            OERenderUtils.drawTexture(TEST_TEXTURE, poseStack, 300, 0, 0, 0, 100, 100, 100, 100);
            OERenderUtils.drawTexture(TEST_TEXTURE, poseStack, 0, 150, 0, 0, 100, 100, 100, 100);
            OERenderUtils.drawTexture(TEST_TEXTURE, poseStack, 0, 300, 0, 0, 100, 100, 100, 100);*/

           /* OERenderUtils.setPreDraw(TEST_TEXTURE);

            OERenderUtils.floatBlit(poseStack, 0, 0, 0, 0, 100, 100, 100, 100);
            OERenderUtils.floatBlit(poseStack, 150, 0, 0, 0, 100, 100, 100, 100);
            OERenderUtils.floatBlit(poseStack, 300, 0, 0, 0, 100, 100, 100, 100);
            OERenderUtils.floatBlit(poseStack, 0, 150, 0, 0, 100, 100, 100, 100);
            OERenderUtils.floatBlit(poseStack, 0, 300, 0, 0, 100, 100, 100, 100);
        });
        //36519
        //
        processTimeMeasure.printResult(1000, 10000);*/

        OERenderUtils.drawFill(poseStack, 0, 0, 150, 150, 0xFF114514);

    }
}
