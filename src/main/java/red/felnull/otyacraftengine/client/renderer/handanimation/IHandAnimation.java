package red.felnull.otyacraftengine.client.renderer.handanimation;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import red.felnull.otyacraftengine.OtyacraftEngine;

public interface IHandAnimation {
    boolean isRender(Hand hand, ItemStack itemStackm, float equipProgress, float interpolatedPitch, float swingProgress);

    default boolean isRenderCancel(Hand hand, ItemStack itemStackm, float equipProgress, float interpolatedPitch, float swingProgress) {
        return isRender(hand, itemStackm, equipProgress, interpolatedPitch, swingProgress);
    }

    default void render(ItemStack itemStack, Hand hand, MatrixStack matrixStack, float partialTicks, IRenderTypeBuffer bufferIn, int light, float equipProgress, float interpolatedPitch, float swingProgress) {
        ClientPlayerEntity pl = OtyacraftEngine.proxy.getMinecraft().player;
        if (pl.getPrimaryHand() == HandSide.RIGHT) {
            if (hand == Hand.MAIN_HAND) {
                if (pl.getHeldItemOffhand().isEmpty()) {
                    renderRightHand(itemStack, matrixStack, partialTicks, bufferIn, light, equipProgress, interpolatedPitch, swingProgress);
                } else {
                    renderRightHandOnry(itemStack, matrixStack, partialTicks, bufferIn, light, equipProgress, interpolatedPitch, swingProgress);
                }
            } else {
                renderLeftHandOnry(itemStack, matrixStack, partialTicks, bufferIn, light, equipProgress, interpolatedPitch, swingProgress);
            }
        } else {
            if (hand == Hand.MAIN_HAND) {
                if (pl.getHeldItemOffhand().isEmpty()) {
                    renderLeftHand(itemStack, matrixStack, partialTicks, bufferIn, light, equipProgress, interpolatedPitch, swingProgress);
                } else {
                    renderLeftHandOnry(itemStack, matrixStack, partialTicks, bufferIn, light, equipProgress, interpolatedPitch, swingProgress);
                }
            } else {
                renderRightHandOnry(itemStack, matrixStack, partialTicks, bufferIn, light, equipProgress, interpolatedPitch, swingProgress);
            }
        }
    }

    void renderLeftHand(ItemStack itemStack, MatrixStack matrixStack, float partialTicks, IRenderTypeBuffer bufferIn, int light, float equipProgress, float interpolatedPitch, float swingProgress);

    void renderRightHand(ItemStack itemStack, MatrixStack matrixStack, float partialTicks, IRenderTypeBuffer bufferIn, int light, float equipProgress, float interpolatedPitch, float swingProgress);

    void renderLeftHandOnry(ItemStack itemStack, MatrixStack matrixStack, float partialTicks, IRenderTypeBuffer bufferIn, int light, float equipProgress, float interpolatedPitch, float swingProgress);

    void renderRightHandOnry(ItemStack itemStack, MatrixStack matrixStack, float partialTicks, IRenderTypeBuffer bufferIn, int light, float equipProgress, float interpolatedPitch, float swingProgress);
}
