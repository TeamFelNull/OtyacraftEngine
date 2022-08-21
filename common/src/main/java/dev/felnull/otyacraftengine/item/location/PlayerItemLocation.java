package dev.felnull.otyacraftengine.item.location;

import dev.felnull.otyacraftengine.item.location.factory.PlayerItemLocationFactory;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface PlayerItemLocation {
    ItemStack getItem(Player player);

    CompoundTag createTag();

    PlayerItemLocationFactory<? extends PlayerItemLocation> getFactory();
}