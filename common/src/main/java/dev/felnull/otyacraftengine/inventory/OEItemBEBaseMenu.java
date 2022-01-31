package dev.felnull.otyacraftengine.inventory;

import dev.felnull.otyacraftengine.item.location.IPlayerItemLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public abstract class OEItemBEBaseMenu extends OEBaseMenu {
    private final BlockPos pos;
    private final ItemStack itemStack;
    private final IPlayerItemLocation location;

    protected OEItemBEBaseMenu(@Nullable MenuType<?> menuType, int windowId, Inventory playerInventory, Container container, BlockPos pos, ItemStack itemStack, IPlayerItemLocation location, int playerSlotX, int playerSlotY) {
        super(menuType, windowId, playerInventory, container, playerSlotX, playerSlotY);
        this.pos = pos;
        this.itemStack = itemStack;
        this.location = location;
    }

    public BlockPos getPos() {
        return pos;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public IPlayerItemLocation getLocation() {
        return location;
    }
}
