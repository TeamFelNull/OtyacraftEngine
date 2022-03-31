package dev.felnull.otyacraftengine.client.event;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.architectury.event.EventResult;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface ClientEvent {
    Event<ChangeHandHeight> CHANGE_HAND_HEIGHT = EventFactory.createEventResult();
    Event<PoseHumanoidArm> POSE_HUMANOID_ARM = EventFactory.createEventResult();
    Event<PauseChange> INTEGRATED_SERVER_PAUSE = EventFactory.createLoop();
    Event<HandAttack> HAND_ATTACK = EventFactory.createEventResult();

    public interface ChangeHandHeight {
        EventResult changeHandHeight(InteractionHand hand, ItemStack oldStack, ItemStack newStack);
    }

    public interface PoseHumanoidArm {
        EventResult poseHumanoidArm(HumanoidArm arm, InteractionHand hand, HumanoidModel<? extends LivingEntity> model, LivingEntity livingEntity);
    }

    public interface PauseChange {
        void onPauseChange(boolean paused);
    }

    public interface HandAttack {
        EventResult onHandAttack(@NotNull ItemStack itemStack);
    }
}
