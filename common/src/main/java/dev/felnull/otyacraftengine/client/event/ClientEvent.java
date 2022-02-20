package dev.felnull.otyacraftengine.client.event;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.architectury.event.EventResult;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface ClientEvent {
    Event<ChangeHandHeight> CHANGE_HAND_HEIGHT = EventFactory.createEventResult();
    Event<PoseHumanoidArm> POSE_HUMANOID_ARM = EventFactory.createEventResult();

    public interface ChangeHandHeight {
        EventResult changeHandHeight(InteractionHand hand, ItemStack oldStack, ItemStack newStack);
    }

    public interface PoseHumanoidArm {
        EventResult poseHumanoidArm(HumanoidArm arm, InteractionHand hand, HumanoidModel<? extends LivingEntity> model, LivingEntity livingEntity);
    }
}
