package dev.felnull.otyacraftengine.item;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public interface IEquipmentItem {
    default EquipmentSlot getEquipmentSlotType(ItemStack stack) {
        return null;
    }
}
