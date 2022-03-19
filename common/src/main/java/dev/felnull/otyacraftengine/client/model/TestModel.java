package dev.felnull.otyacraftengine.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.client.gui.screen.debug.RenderTestScreen;
import dev.felnull.otyacraftengine.client.gui.screen.debug.rendertest.IRenderTest;
import net.minecraft.client.renderer.MultiBufferSource;
import org.jetbrains.annotations.NotNull;

public class TestModel {
    public static void init() {
        RenderTestScreen.addRenderTest(new TestRender());
    }

    public static class TestRender implements IRenderTest {
        @Override
        public void renderTest(@NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, float f) {
            //      var vc = multiBufferSource.getBuffer(Sheets.cutoutBlockSheet());
            //OERenderUtil.renderTextureSprite(IOEBaseComponent.WIDGETS, poseStack, multiBufferSource, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);

        }
    }
}
