package dev.felnull.otyacraftengine.forge.mixin.client;

import dev.felnull.otyacraftengine.api.event.client.OEClientHooks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    private ItemStack mainHandItem;

    @Shadow
    private float mainHandHeight;

    @Shadow
    private ItemStack offHandItem;

    @Shadow
    private float offHandHeight;

    @ModifyArgs(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(FFF)F", ordinal = 2))
    private void tickMainHandShake(Args args) {
        var item = minecraft.player.getMainHandItem();
        if (!OEClientHooks.onChangeHandHeight(InteractionHand.MAIN_HAND, this.mainHandItem, item)) {
            this.mainHandItem = item;
            args.set(0, 1f - mainHandHeight);
        }
    }

    @ModifyArgs(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(FFF)F", ordinal = 3))
    private void tickOffHandShake(Args args) {
        var item = minecraft.player.getOffhandItem();
        if (!OEClientHooks.onChangeHandHeight(InteractionHand.OFF_HAND, this.offHandItem, item)) {
            this.offHandItem = item;
            args.set(0, 1f - offHandHeight);
        }
    }
}
