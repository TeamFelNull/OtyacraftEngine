package dev.felnull.otyacraftengine.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public interface IInstructionItem {
    CompoundTag onInstruction(ItemStack stack, ServerPlayer player, String name, int num, CompoundTag data);

    default boolean canInstructionWith(ItemStack stack, ServerPlayer player, String name, int num, CompoundTag data) {
        return !stack.isEmpty();
    }
}
