package dev.felnull.otyacraftengine.api.event.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public class OEClientHooks {
    public static boolean onRenderHand(PoseStack poseStack, MultiBufferSource multiBufferSource, InteractionHand hand, int packedLight, float partialTicks, float interpolatedPitch, float swingProgress, float equipProgress, ItemStack stack) {
        return !MoreRenderEvent.RENDER_ITEM_IN_HAND.invoker().renderItemInHand(poseStack, multiBufferSource, hand, packedLight, partialTicks, interpolatedPitch, swingProgress, equipProgress, stack).isFalse();
    }

    public static boolean onChangeHandHeight(InteractionHand hand, ItemStack oldStack, ItemStack newStack) {
        var event = ClientEvent.CHANGE_HAND_HEIGHT.invoker().changeHandHeight(hand, oldStack, newStack);
        return event.isEmpty() || event.isTrue();
    }
}
