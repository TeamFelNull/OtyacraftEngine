package dev.felnull.otyacraftengine.item.location;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class HandItemLocation implements IPlayerItemLocation {
    private InteractionHand hand;

    public HandItemLocation(InteractionHand hand) {
        this.hand = hand;
    }

    public static HandItemLocation factory(CompoundTag tag) {
        return new HandItemLocation(tag.getBoolean("Hand") ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND);
    }

    public InteractionHand getHand() {
        return hand;
    }

    @Override
    public ItemStack getItem(Player player) {
        return player.getItemInHand(hand);
    }

    @Override
    public CompoundTag toTag() {
        var tag = new CompoundTag();
        tag.putBoolean("Hand", hand == InteractionHand.MAIN_HAND);
        return tag;
    }

    @Override
    public ResourceLocation getResourceLocation() {
        return PlayerItemLocations.HAND;
    }
}
