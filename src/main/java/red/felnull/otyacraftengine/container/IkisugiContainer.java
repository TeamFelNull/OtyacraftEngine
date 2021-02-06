package red.felnull.otyacraftengine.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.stream.IntStream;

public abstract class IkisugiContainer extends Container {
    protected IInventory inventory;
    protected BlockPos pos;
    protected PlayerInventory playerInventory;

    public IkisugiContainer(@Nullable ContainerType<?> type, int windowId, PlayerInventory playerInventory, IInventory inventory, BlockPos pos, int plslotX, int plslotY) {
        super(type, windowId);
        this.inventory = inventory;
        this.pos = pos;
        this.playerInventory = playerInventory;
        inventory.openInventory(playerInventory.player);
        setSlot();
        //def 8 142
        setPlayerSlot(plslotX, plslotY);
    }

    public IkisugiContainer(@Nullable ContainerType<?> type, int windowId, PlayerInventory playerInventory, IInventory inventory, int plslotX, int plslotY) {
        super(type, windowId);
        this.inventory = inventory;
        this.playerInventory = playerInventory;
        inventory.openInventory(playerInventory.player);
        setSlot();
        //def 8 142
        setPlayerSlot(plslotX, plslotY);
    }

    public IkisugiContainer(@Nullable ContainerType<?> type, int windowId, PlayerInventory playerInventory, IInventory inventory, BlockPos pos) {
        super(type, windowId);
        this.inventory = inventory;
        this.playerInventory = playerInventory;
        this.pos = pos;
        inventory.openInventory(playerInventory.player);
        setSlot();
    }

    public IkisugiContainer(@Nullable ContainerType<?> type, int windowId, PlayerInventory playerInventory, IInventory inventory) {
        super(type, windowId);
        this.inventory = inventory;
        this.playerInventory = playerInventory;
        inventory.openInventory(playerInventory.player);
        setSlot();
    }


    public IkisugiContainer(@Nullable ContainerType<?> type, int windowId, PlayerInventory playerInventory, int plslotX, int plslotY) {
        super(type, windowId);
        this.playerInventory = playerInventory;
        setSlot();
        //def 8 142
        setPlayerSlot(plslotX, plslotY);
    }

    public IkisugiContainer(@Nullable ContainerType<?> type, int windowId, PlayerInventory playerInventory, BlockPos pos, int plslotX, int plslotY) {
        super(type, windowId);
        this.pos = pos;
        this.playerInventory = playerInventory;
        setSlot();
        //def 8 142
        setPlayerSlot(plslotX, plslotY);
    }

    public IkisugiContainer(@Nullable ContainerType<?> type, int windowId, BlockPos pos) {
        super(type, windowId);
        this.pos = pos;
    }

    public IkisugiContainer(@Nullable ContainerType<?> type, int windowId) {
        super(type, windowId);
    }

    protected void setSlot() {

    }

    protected void setPlayerSlot(int x, int y) {
        if (playerInventory == null)
            return;
        IntStream.range(0, 3).forEach(k -> IntStream.range(0, 9).forEach(i1 -> this.addSlot(new Slot(playerInventory, i1 + k * 9 + 9, x + i1 * 18, y + k * 18))));
        IntStream.range(0, 9).forEach(l -> this.addSlot(new Slot(playerInventory, l, x + l * 18, y + 58)));
    }

    public IInventory getIInventory() {
        return inventory;
    }

    public BlockPos getPos() {
        return pos;
    }

    public PlayerInventory getPlayerInventory() {
        return playerInventory;
    }

    @Override
    public void onContainerClosed(PlayerEntity playerEntity) {
        super.onContainerClosed(playerEntity);
        if (inventory != null) {
            this.inventory.closeInventory(playerEntity);
        }
    }
}
