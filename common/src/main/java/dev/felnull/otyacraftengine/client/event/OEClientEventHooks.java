package dev.felnull.otyacraftengine.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class OEClientEventHooks {
    public static void onLevelUnload(ClientLevel level) {
        MoreClientLifecycleEvents.CLIENT_LEVEL_UNLOAD.invoker().act(level);
    }

    public static boolean onOBJLoaderCheck(ResourceLocation resourceId) {
        return OBJLoaderEvent.LOAD_CHECK.invoker().loadCheck(resourceId).isTrue();
    }

    public static boolean onRenderHand(PoseStack poseStack, MultiBufferSource multiBufferSource, InteractionHand hand, int packedLight, float partialTicks, float interpolatedPitch, float swingProgress, float equipProgress, ItemStack stack) {
        return !MoreRenderEvent.RENDER_ITEM_IN_HAND.invoker().renderItemInHand(poseStack, multiBufferSource, hand, packedLight, partialTicks, interpolatedPitch, swingProgress, equipProgress, stack).isFalse();
    }

    public static boolean onRenderArmWithItem(ItemInHandLayer<? extends LivingEntity, ? extends EntityModel<?>> layer, LivingEntity livingEntity, ItemStack itemStack, ItemTransforms.TransformType transformType, HumanoidArm humanoidArm, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        var event = MoreRenderEvent.RENDER_ARM_WITH_ITEM.invoker().renderArmWithItem(layer, livingEntity, itemStack, transformType, humanoidArm, poseStack, multiBufferSource, i);
        return event.isEmpty() || event.isTrue();
    }
}
