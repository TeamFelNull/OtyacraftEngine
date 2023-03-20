package dev.felnull.otyacraftenginetest.client.handler;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientRawInputEvent;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import dev.felnull.otyacraftengine.client.event.ClientCameraEvent;
import dev.felnull.otyacraftengine.client.event.MoreRenderEvent;
import dev.felnull.otyacraftengine.client.util.OEClientUtils;
import dev.felnull.otyacraftengine.client.util.OERenderUtils;
import dev.felnull.otyacraftenginetest.client.gui.screen.TestSelectScreen;
import net.minecraft.client.Camera;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.EnderEyeItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.FogType;
import org.lwjgl.glfw.GLFW;

public class ClientHandler {
    private static final Minecraft mc = Minecraft.getInstance();
    public static final KeyMapping TEST_KEY = new KeyMapping("key.test.test", GLFW.GLFW_KEY_J, "key.categories.test");

    public static void init() {
        ClientRawInputEvent.KEY_PRESSED.register(ClientHandler::onKeyPressed);
        MoreRenderEvent.RENDER_ITEM_IN_HAND.register(ClientHandler::onRenderItemInHand);
        MoreRenderEvent.RENDER_ARM_WITH_ITEM.register(ClientHandler::renderArmWithItem);

        ClientCameraEvent.RENDER_FOG.register(ClientHandler::onRenderFog);
        ClientCameraEvent.COMPUTE_FOG_COLOR.register(ClientHandler::onComputeFogColor);

        KeyMappingRegistry.register(TEST_KEY);
    }

    private static EventResult onComputeFogColor(Camera camera, float red, float green, float blue, double delta, ClientCameraEvent.FogColorSetter fogColorSetter) {
        if (Minecraft.getInstance().player.getItemInHand(InteractionHand.OFF_HAND).is(Items.BOOK)) {
            fogColorSetter.setRed(0f);
            fogColorSetter.setGreen(0f);
            fogColorSetter.setBlue(1f);
        }
        return EventResult.pass();
    }

    private static EventResult onRenderFog(Camera camera, FogRenderer.FogMode fogMode, FogType fogType, float startDistance, float endDistance, FogShape fogShape, double delta, ClientCameraEvent.RenderFogSetter setter) {

        if (Minecraft.getInstance().player.getItemInHand(InteractionHand.MAIN_HAND).is(Items.INK_SAC)) {

            System.out.println(startDistance + ":" + endDistance + ":" + fogShape);

            setter.setStartDistance(0.25F);
            setter.setEndDistance(1.0F);
            setter.setFogShape(FogShape.SPHERE);
            return EventResult.interruptFalse();
        }

        return EventResult.pass();
    }

    private static EventResult onKeyPressed(Minecraft client, int keyCode, int scanCode, int action, int modifiers) {
        if (keyCode == OEClientUtils.getKey(TEST_KEY).getValue()) {
            client.setScreen(new TestSelectScreen());
        }
        return EventResult.interruptDefault();
    }

    private static EventResult renderArmWithItem(ItemInHandLayer<? extends LivingEntity, ? extends EntityModel<?>> layer, LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext displayContext, HumanoidArm humanoidArm, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        if (itemStack.is(Items.APPLE)) {
            poseStack.pushPose();
            ModelPart modelPart = ((HeadedModel) layer.getParentModel()).getHead();
            float f = modelPart.xRot;
            modelPart.xRot = Mth.clamp(modelPart.xRot, -0.5235988F, 1.5707964F);
            modelPart.translateAndRotate(poseStack);
            modelPart.xRot = f;
            CustomHeadLayer.translateToHead(poseStack, false);
            boolean bl = humanoidArm == HumanoidArm.LEFT;
            poseStack.translate((bl ? -2.5F : 2.5F) / 16.0F, -0.0625D, 0.0D);
            Minecraft.getInstance().gameRenderer.itemInHandRenderer.renderItem(livingEntity, itemStack, ItemDisplayContext.HEAD, false, poseStack, multiBufferSource, i);
            poseStack.popPose();
            return EventResult.interruptFalse();
        }
        return EventResult.pass();
    }

    private static EventResult onRenderItemInHand(PoseStack poseStack, MultiBufferSource multiBufferSource, InteractionHand hand, int packedLight, float partialTicks, float interpolatedPitch, float swingProgress, float equipProgress, ItemStack stack) {
        if (stack.getItem() instanceof EnderEyeItem) {
            boolean bl = hand == InteractionHand.MAIN_HAND;
            HumanoidArm arm = bl ? mc.player.getMainArm() : mc.player.getMainArm().getOpposite();
            poseStack.pushPose();
            poseStack.pushPose();
            OERenderUtils.posePlayerArm(poseStack, arm, swingProgress, equipProgress);
            OERenderUtils.renderPlayerArm(poseStack, multiBufferSource, arm, packedLight);
            poseStack.popPose();
            poseStack.pushPose();
            OERenderUtils.poseHandItem(poseStack, arm, swingProgress, equipProgress);
            OERenderUtils.renderHandItem(poseStack, multiBufferSource, arm, stack, packedLight);
            poseStack.popPose();
            poseStack.popPose();
            return EventResult.interruptFalse();
        }
        return EventResult.pass();
    }
}
