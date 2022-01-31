package dev.felnull.otyacraftengine.inventory;

import dev.felnull.otyacraftengine.item.location.IPlayerItemLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public abstract class OEItemBaseMenu extends OEBaseMenu {
    private final ItemStack itemStack;
    private final IPlayerItemLocation location;

    protected OEItemBaseMenu(@Nullable MenuType<?> menuType, int i, Inventory playerInventory, Container container, ItemStack stack, IPlayerItemLocation location, int playerSlotX, int playerSlotY) {
        super(menuType, i, playerInventory, container, playerSlotX, playerSlotY);
        this.itemStack = stack;
        this.location = location;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public IPlayerItemLocation getLocation() {
        return location;
    }
}
