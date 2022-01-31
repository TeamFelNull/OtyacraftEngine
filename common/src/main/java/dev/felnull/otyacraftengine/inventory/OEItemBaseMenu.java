package dev.felnull.otyacraftengine.inventory;

import dev.felnull.otyacraftengine.inventory.slot.LockedSlot;
import dev.felnull.otyacraftengine.item.location.IPlayerItemLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.stream.IntStream;

public abstract class OEItemBaseMenu extends OEBaseMenu {
    private final ItemStack itemStack;
    private final IPlayerItemLocation location;

    protected OEItemBaseMenu(@Nullable MenuType<?> menuType, int i, Inventory playerInventory, Container container, ItemStack stack, IPlayerItemLocation location, int playerSlotX, int playerSlotY) {
        super(menuType, i, playerInventory, container, -1, -1);
        this.itemStack = stack;
        this.location = location;
        setPlayerSlot(playerSlotX, playerSlotY);
    }

    @Override
    protected void setPlayerSlot(int x, int y) {
        if (x >= 0 && y >= 0) {
            IntStream.range(0, 3).forEach(k -> IntStream.range(0, 9).forEach(i1 -> this.addSlot(new LockedSlot(getPlayerInventory(), itemStack, i1 + k * 9 + 9, x + i1 * 18, y + k * 18))));
            IntStream.range(0, 9).forEach(l -> this.addSlot(new LockedSlot(getPlayerInventory(), itemStack, l, x + l * 18, y + 58)));
        }
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public IPlayerItemLocation getLocation() {
        return location;
    }
}
