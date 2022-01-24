package dev.felnull.otyacraftengine.api.event.client;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.architectury.event.EventResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public interface ClientEvent {
    Event<ChangeHandHeight> CHANGE_HAND_HEIGHT = EventFactory.createEventResult();

    public interface ChangeHandHeight {
        EventResult changeHandHeight(InteractionHand hand, ItemStack oldStack, ItemStack newStack);
    }
}
