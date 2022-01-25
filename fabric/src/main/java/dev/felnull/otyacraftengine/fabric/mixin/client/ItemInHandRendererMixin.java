package dev.felnull.otyacraftengine.fabric.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.api.event.client.OEClientHooks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {
    @Shadow
    protected abstract void renderArmWithItem(AbstractClientPlayer abstractClientPlayer, float f, float g, InteractionHand interactionHand, float h, ItemStack itemStack, float i, PoseStack poseStack, MultiBufferSource multiBufferSource, int j);

    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    private ItemStack mainHandItem;

    @Shadow
    private ItemStack offHandItem;

    @Redirect(method = "renderHandsWithItems", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderArmWithItem(Lnet/minecraft/client/player/AbstractClientPlayer;FFLnet/minecraft/world/InteractionHand;FLnet/minecraft/world/item/ItemStack;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"))
    private void injected(ItemInHandRenderer instance, AbstractClientPlayer abstractClientPlayer, float f, float g, InteractionHand interactionHand, float h, ItemStack itemStack, float i, PoseStack poseStack, MultiBufferSource multiBufferSource, int j) {
        if (OEClientHooks.onRenderHand(poseStack, multiBufferSource, interactionHand, j, f, g, h, i, itemStack))
            renderArmWithItem(abstractClientPlayer, f, g, interactionHand, h, itemStack, i, poseStack, multiBufferSource, j);
    }


    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isHandsBusy()Z"))
    private void tick(CallbackInfo ci) {
        var mitem = minecraft.player.getMainHandItem();
        if (!OEClientHooks.onChangeHandHeight(InteractionHand.MAIN_HAND, this.mainHandItem, mitem)) {
            this.mainHandItem = mitem;
        }

        var oitem = minecraft.player.getOffhandItem();
        if (!OEClientHooks.onChangeHandHeight(InteractionHand.OFF_HAND, this.offHandItem, oitem)) {
            this.offHandItem = oitem;
        }
    }
}
