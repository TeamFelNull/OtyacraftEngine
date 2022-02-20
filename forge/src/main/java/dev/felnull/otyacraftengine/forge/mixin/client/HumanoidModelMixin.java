package dev.felnull.otyacraftengine.forge.mixin.client;


import dev.felnull.otyacraftengine.client.event.OEClientEventHooks;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public class HumanoidModelMixin<T extends LivingEntity> {
    @Inject(method = "poseRightArm", at = @At("HEAD"), cancellable = true)
    private void poseRightArm(T livingEntity, CallbackInfo ci) {
        var hand = livingEntity.getMainArm() == HumanoidArm.RIGHT ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
        if (!OEClientEventHooks.poseHumanoidArm(HumanoidArm.RIGHT, hand, (HumanoidModel<T>) (Object) this, livingEntity))
            ci.cancel();
    }

    @Inject(method = "poseLeftArm", at = @At("HEAD"), cancellable = true)
    private void poseLeftArm(T livingEntity, CallbackInfo ci) {
        var hand = livingEntity.getMainArm() == HumanoidArm.LEFT ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
        if (!OEClientEventHooks.poseHumanoidArm(HumanoidArm.LEFT, hand, (HumanoidModel<T>) (Object) this, livingEntity))
            ci.cancel();
    }
}
