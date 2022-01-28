package dev.felnull.otyacraftengine.blockentity;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;

public interface ItemDroppedBlockEntity {
    public NonNullList<ItemStack> getDroppedItems();

    default public boolean isRetainDrop() {
        return false;
    }
}
