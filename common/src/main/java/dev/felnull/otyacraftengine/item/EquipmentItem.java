package dev.felnull.otyacraftengine.item;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface EquipmentItem {
    @Nullable EquipmentSlot getEquipmentSlotType(ItemStack stack);
}