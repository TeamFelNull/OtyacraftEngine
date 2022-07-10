package net.examplemod.client.handler;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientRawInputEvent;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import dev.felnull.otyacraftengine.client.event.MoreRenderEvent;
import dev.felnull.otyacraftengine.client.event.OBJLoaderEvent;
import dev.felnull.otyacraftengine.client.util.OEClientUtils;
import dev.felnull.otyacraftengine.client.util.OERenderUtils;
import net.examplemod.ExampleMod;
import net.examplemod.client.gui.screen.TestScreen;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.EnderEyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.lwjgl.glfw.GLFW;

public class ClientHandler {
    public static final KeyMapping TEST_KEY = new KeyMapping("key.test.test", GLFW.GLFW_KEY_J, "key.categories.test");
    private static final Minecraft mc = Minecraft.getInstance();

    public static void init() {
        KeyMappingRegistry.register(TEST_KEY);
        ClientRawInputEvent.KEY_PRESSED.register(ClientHandler::onKeyPressed);
        OBJLoaderEvent.LOAD_CHECK.register(ClientHandler::onObjLoadCheck);
        MoreRenderEvent.RENDER_ITEM_IN_HAND.register(ClientHandler::onRenderItemInHand);
        MoreRenderEvent.RENDER_ARM_WITH_ITEM.register(ClientHandler::renderArmWithItem);
    }

    private static EventResult renderArmWithItem(ItemInHandLayer<? extends LivingEntity, ? extends EntityModel<?>> layer, LivingEntity livingEntity, ItemStack itemStack, ItemTransforms.TransformType transformType, HumanoidArm humanoidArm, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
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
            Minecraft.getInstance().gameRenderer.itemInHandRenderer.renderItem(livingEntity, itemStack, ItemTransforms.TransformType.HEAD, false, poseStack, multiBufferSource, i);
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

    private static EventResult onKeyPressed(Minecraft client, int keyCode, int scanCode, int action, int modifiers) {
        if (keyCode == OEClientUtils.getKey(TEST_KEY).getValue()) {
            client.setScreen(new TestScreen());
        }
        return EventResult.interruptDefault();
    }

    private static EventResult onObjLoadCheck(ResourceLocation location) {
        if (ExampleMod.MODID.equals(location.getNamespace()))
            return EventResult.interruptTrue();
        return EventResult.pass();
    }
}
