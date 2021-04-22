package red.felnull.otyacraftengine.inventory;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class IkisugiBaseContainerMenu extends IkisugiContainerMenu {
    private final Container container;
    private final Inventory inventory;

    protected IkisugiBaseContainerMenu(@Nullable MenuType<?> menuType, int windowId, BlockPos pos, Container container, Inventory inventory, int plslotX, int plslotY) {
        super(menuType, windowId, pos);
        this.container = container;
        this.inventory = inventory;
        setSlot();
        setPlayerSlot(plslotX, plslotY);
    }

    protected abstract void setSlot();

    protected void setPlayerSlot(int x, int y) {
        IntStream.range(0, 3).forEach(k -> IntStream.range(0, 9).forEach(i1 -> this.addSlot(new Slot(inventory, i1 + k * 9 + 9, x + i1 * 18, y + k * 18))));
        IntStream.range(0, 9).forEach(l -> this.addSlot(new Slot(inventory, l, x + l * 18, y + 58)));
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Container getContainer() {
        return container;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.container.stillValid(player);
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.container.stopOpen(player);
    }

    public int getNonPlayerInventorySlotCount() {
        return getNonPlayerInventorySlots().size();
    }

    public List<Slot> getNonPlayerInventorySlots() {
        return slots.stream().filter(n -> n.container != getInventory()).collect(Collectors.toList());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack slotitem = slot.getItem();
            itemstack = slotitem.copy();
            if (index <= getNonPlayerInventorySlotCount() - 1) {
                if (!this.moveItemStackTo(slotitem, getNonPlayerInventorySlotCount(), 36 + getNonPlayerInventorySlotCount(), false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index <= getNonPlayerInventorySlotCount() + 26) {
                for (int i = 0; i < getNonPlayerInventorySlotCount(); i++) {
                    if (getNonPlayerInventorySlots().get(i).mayPlace(slotitem) && !this.moveItemStackTo(slotitem, i, i + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                if (!this.moveItemStackTo(slotitem, getNonPlayerInventorySlotCount() + 27, getNonPlayerInventorySlotCount() + 36, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                for (int i = 0; i < getNonPlayerInventorySlotCount(); i++) {
                    if (getNonPlayerInventorySlots().get(i).mayPlace(slotitem) && !this.moveItemStackTo(slotitem, i, i + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                if (!this.moveItemStackTo(slotitem, getNonPlayerInventorySlotCount(), getNonPlayerInventorySlotCount() + 26, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (slotitem.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (slotitem.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, slotitem);
        }
        return itemstack;
    }

}
