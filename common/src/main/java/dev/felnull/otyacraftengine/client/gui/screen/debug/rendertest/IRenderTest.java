package dev.felnull.otyacraftengine.client.gui.screen.debug.rendertest;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import org.jetbrains.annotations.NotNull;

public interface IRenderTest {
    public void renderTest(@NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, float f);
}
