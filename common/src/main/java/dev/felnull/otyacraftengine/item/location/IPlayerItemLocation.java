package dev.felnull.otyacraftengine.item.location;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IPlayerItemLocation {

    public ItemStack getItem(Player player);

    public CompoundTag toTag();

    public ResourceLocation getResourceLocation();
}
