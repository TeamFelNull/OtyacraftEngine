package dev.felnull.otyacraftengine.client.handler;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientRawInputEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.client.debug.MotionDebug;
import dev.felnull.otyacraftengine.client.debug.socket.SocketDebugService;
import dev.felnull.otyacraftengine.client.event.ClientEvent;
import dev.felnull.otyacraftengine.client.event.FabricOBJLoaderEvent;
import dev.felnull.otyacraftengine.client.event.MoreRenderEvent;
import dev.felnull.otyacraftengine.client.gui.components.FixedButtonsList;
import dev.felnull.otyacraftengine.client.gui.screen.DebugScreen;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import dev.felnull.otyacraftengine.impl.client.OEClientExpectPlatform;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.EnderEyeItem;
import net.minecraft.world.item.ItemStack;
import org.lwjgl.glfw.GLFW;

import java.util.List;
import java.util.function.Function;

public class TestClientHandler {
    public static final KeyMapping TEST_KEY = new KeyMapping("key.otyacraftengine.test", GLFW.GLFW_KEY_J, "key.categories.otyacraftengine");
    private static final Minecraft mc = Minecraft.getInstance();

    public static void init() {
        ClientRawInputEvent.KEY_PRESSED.register(TestClientHandler::onKeyPressed);
        FabricOBJLoaderEvent.LOAD.register(TestClientHandler::onFabricOBJLoad);
        MoreRenderEvent.RENDER_ITEM_IN_HAND.register(TestClientHandler::onRenderHand);
        ClientEvent.CHANGE_HAND_HEIGHT.register(TestClientHandler::changeHandHeight);
        ClientEvent.POSE_HUMANOID_ARM.register(TestClientHandler::poseHumanoidArm);
        KeyMappingRegistry.register(TEST_KEY);
        MoreRenderEvent.RENDER_ARM_WITH_ITEM.register(TestClientHandler::renderArmWithItem);
        ClientEvent.INTEGRATED_SERVER_PAUSE.register(TestClientHandler::onIntegratedPauseChange);
        ClientTickEvent.CLIENT_POST.register(TestClientHandler::ontClientTick);
        ClientEvent.HAND_ATTACK.register(TestClientHandler::onHandAttack);
    }

    private static EventResult onHandAttack(ItemStack itemStack) {
        //    if (itemStack.is(Items.DIAMOND))
        //        return EventResult.interruptFalse();
        return EventResult.pass();
    }

    private static void ontClientTick(Minecraft instance) {
        SocketDebugService.tick();
    }

    public static void onIntegratedPauseChange(boolean paused) {
        // System.out.println(paused);
    }

    public static EventResult onKeyPressed(Minecraft client, int keyCode, int scanCode, int action, int modifiers) {
        //   System.out.println(OEClientUtil.isKeyInput(OEClientExpectPlatform.getKey(TEST_KEY).getValue()));
        if (keyCode == OEClientExpectPlatform.getKey(TEST_KEY).getValue()) {
            client.setScreen(new DebugScreen());
        }
        return EventResult.interruptDefault();
    }

    public static EventResult onFabricOBJLoad(ResourceLocation location) {
        if (location.getNamespace().equals(OtyacraftEngine.MODID))
            return EventResult.interruptTrue();
        return EventResult.pass();
    }

    public static EventResult onRenderHand(PoseStack poseStack, MultiBufferSource multiBufferSource, InteractionHand hand, int packedLight, float partialTicks, float interpolatedPitch, float swingProgress, float equipProgress, ItemStack stack) {
        if (stack.getItem() instanceof EnderEyeItem) {
            boolean bl = hand == InteractionHand.MAIN_HAND;
            HumanoidArm arm = bl ? mc.player.getMainArm() : mc.player.getMainArm().getOpposite();
            poseStack.pushPose();
            MotionDebug.getInstance().onDebug(poseStack, multiBufferSource, .5f);
            poseStack.pushPose();
            OERenderUtil.posePlayerArm(poseStack, arm, swingProgress, equipProgress);
            OERenderUtil.renderPlayerArm(poseStack, multiBufferSource, arm, packedLight);
            poseStack.popPose();
            poseStack.pushPose();
            OERenderUtil.poseHandItem(poseStack, arm, swingProgress, equipProgress);
            OERenderUtil.renderHandItem(poseStack, multiBufferSource, arm, stack, packedLight);
            poseStack.popPose();
            poseStack.popPose();
            return EventResult.interruptFalse();
        }
        return EventResult.pass();
    }

    public static EventResult changeHandHeight(InteractionHand hand, ItemStack oldStack, ItemStack newStack) {
       /* if (oldStack.getItem() == newStack.getItem()) {
            if (oldStack.getDisplayName().getString().equals(newStack.getDisplayName().getString())) {
                return EventResult.interruptFalse();
            }
        }*/

      //  if (hand == InteractionHand.MAIN_HAND)
      //      System.out.println(oldStack.hashCode() +" : "+ newStack.hashCode());
        return EventResult.pass();
    }

    public static EventResult poseHumanoidArm(HumanoidArm arm, InteractionHand hand, HumanoidModel<? extends LivingEntity> model, LivingEntity livingEntity) {
       /* if (hand == InteractionHand.MAIN_HAND && livingEntity.getMainHandItem().is(Items.BOW)) {
            model.rightArm.xRot = (float) (-OERenderUtil.getParSecond(1000) * Math.PI);
            model.leftArm.xRot = (float) (-(1f - OERenderUtil.getParSecond(1000)) * Math.PI);
            return EventResult.interruptFalse();
        }*/
        return EventResult.pass();
    }

    public static EventResult renderArmWithItem(ItemInHandLayer<? extends LivingEntity, ? extends EntityModel<?>> layer, LivingEntity livingEntity, ItemStack itemStack, ItemTransforms.TransformType transformType, HumanoidArm humanoidArm, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        /*if (itemStack.is(Items.APPLE)) {
            poseStack.pushPose();
            ModelPart modelPart = ((HeadedModel) layer.getParentModel()).getHead();
            float f = modelPart.xRot;
            modelPart.xRot = Mth.clamp(modelPart.xRot, -0.5235988F, 1.5707964F);
            modelPart.translateAndRotate(poseStack);
            modelPart.xRot = f;
            CustomHeadLayer.translateToHead(poseStack, false);
            boolean bl = humanoidArm == HumanoidArm.LEFT;
            poseStack.translate((bl ? -2.5F : 2.5F) / 16.0F, -0.0625D, 0.0D);
            Minecraft.getInstance().getItemInHandRenderer().renderItem(livingEntity, itemStack, ItemTransforms.TransformType.HEAD, false, poseStack, multiBufferSource, i);
            poseStack.popPose();
            return EventResult.interruptFalse();
        }*/
        return EventResult.pass();
    }

    private static class PointFixedButtonsList extends FixedButtonsList<MotionPoint> {

        public PointFixedButtonsList(int x, int y, int w, int h, ResourceLocation resource, int tx, int ty, int tw, int th, int num, Component name, List<MotionPoint> list, Function<MotionPoint, Component> listName, PressEntry<MotionPoint> onPressEntry) {
            super(x, y, w, h, resource, tx, ty, tw, th, num, name, list, listName, onPressEntry);
        }
    }

    private static record MotionPoint(Vector3f position, Vector3f rotation, Vector3f scale) {

    }
}
