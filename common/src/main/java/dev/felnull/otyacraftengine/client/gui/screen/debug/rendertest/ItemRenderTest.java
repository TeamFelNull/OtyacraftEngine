package dev.felnull.otyacraftengine.client.gui.screen.debug.rendertest;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.client.gui.IOEBaseGUI;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

public class ItemRenderTest implements IRenderTest, IOEBaseGUI {
    private final ItemStack stack;

    public ItemRenderTest(ItemLike itemLike) {
        this.stack = new ItemStack(itemLike);
    }

    public ItemRenderTest(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public void renderTest(@NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, float f) {
        mc.getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.FIXED, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, 0);
    }
}
