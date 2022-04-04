package dev.felnull.otyacraftengine.util;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;

public class OEEntityUtil {
    public static InteractionHand getHandByArm(LivingEntity entity, HumanoidArm arm) {
        return entity.getMainArm() == arm ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
    }

    public static HumanoidArm getArmByHand(LivingEntity entity, InteractionHand hand) {
        return hand == InteractionHand.MAIN_HAND ? entity.getMainArm() : entity.getMainArm().getOpposite();
    }
}
