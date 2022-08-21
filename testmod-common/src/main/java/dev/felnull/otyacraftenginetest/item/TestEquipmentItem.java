package dev.felnull.otyacraftenginetest.item;

import dev.felnull.otyacraftengine.item.EquipmentItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class TestEquipmentItem extends Item implements EquipmentItem {
    public TestEquipmentItem(Properties properties) {
        super(properties);
    }

    @Override
    public EquipmentSlot getEquipmentSlotType(ItemStack stack) {
        return EquipmentSlot.HEAD;
    }
}
