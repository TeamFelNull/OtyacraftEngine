package dev.felnull.otyacraftengine.forge.mixin.client;

import dev.felnull.otyacraftengine.client.event.OEClientEventHooks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {
    @Shadow
    @Final
    private Minecraft minecraft;
    @Shadow
    private ItemStack mainHandItem;
    @Shadow
    private ItemStack offHandItem;

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isHandsBusy()Z"))
    private void tick(CallbackInfo ci) {
        var mitem = minecraft.player.getMainHandItem();
        if (!OEClientEventHooks.onChangeHandHeight(InteractionHand.MAIN_HAND, this.mainHandItem, mitem)) {
            this.mainHandItem = mitem;
        }

        var oitem = minecraft.player.getOffhandItem();
        if (!OEClientEventHooks.onChangeHandHeight(InteractionHand.OFF_HAND, this.offHandItem, oitem)) {
            this.offHandItem = oitem;
        }
    }
}
