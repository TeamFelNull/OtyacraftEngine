package dev.felnull.otyacraftengine.client.gui.screen.debug.rendertest;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.client.gui.IOEBaseGUI;
import dev.felnull.otyacraftengine.client.util.OEModelUtil;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class BakedModelRenderTest implements IRenderTest, IOEBaseGUI {
    private final ResourceLocation modelLocation;

    public BakedModelRenderTest(ResourceLocation modelLocation) {
        this.modelLocation = modelLocation;
    }

    @Override
    public void renderTest(@NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, float f) {
        var model = OEModelUtil.getModel(modelLocation);
        OERenderUtil.renderModel(poseStack, multiBufferSource.getBuffer(Sheets.cutoutBlockSheet()), model, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
    }
}