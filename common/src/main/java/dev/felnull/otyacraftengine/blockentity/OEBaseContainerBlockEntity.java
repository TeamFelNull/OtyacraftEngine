package dev.felnull.otyacraftengine.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public abstract class OEBaseContainerBlockEntity extends BaseContainerBlockEntity implements DroppedBlockEntity, ClientSyncableBlockEntity {
    protected OEBaseContainerBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    @NotNull
    abstract public NonNullList<ItemStack> getItems();

    @Override
    public int getContainerSize() {
        return getItems().size();
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        if (!isSyncUpdate()) return null;
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        var tag = super.getUpdateTag();
        saveToUpdateTag(tag);
        return tag;
    }

    @Override
    public void saveToUpdateTag(CompoundTag tag) {
        ContainerHelper.saveAllItems(tag, getItems());
    }

    @Override
    public boolean isSyncUpdate() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return getItems().stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public ItemStack getItem(int i) {
        return getItems().get(i);
    }

    @Override
    public ItemStack removeItem(int i, int j) {
        return ContainerHelper.removeItem(getItems(), i, j);
    }

    @Override
    public ItemStack removeItemNoUpdate(int i) {
        return ContainerHelper.takeItem(getItems(), i);
    }

    @Override
    public void setItem(int i, ItemStack stack) {
        ItemStack itemstack = getItems().get(i);
        boolean flag = !stack.isEmpty() && stack.sameItem(itemstack) && ItemStack.tagMatches(stack, itemstack);
        getItems().set(i, stack);
        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }
        if (flag)
            this.setChanged();

        syncToClient();
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        if (this.level.getBlockEntity(this.worldPosition) != this)
            return false;
        return isUsableByPlayer(player);
    }

    @Override
    public void clearContent() {
        getItems().clear();
    }

    public boolean isUsableByPlayer(Player player) {
        return getLevel().getBlockEntity(getBlockPos()) == this && player.distanceToSqr((double) getBlockPos().getX() + 0.5D, (double) getBlockPos().getY() + 0.5D, (double) getBlockPos().getZ() + 0.5D) <= 64.0D;
    }

    @Override
    public Collection<ItemStack> getDroppedItems() {
        return getItems();
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        ContainerHelper.loadAllItems(tag, getItems());
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, getItems());
    }

    protected ItemStack getDropItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack createRetainDropItem() {
        var itm = getDropItem();
        if (itm.isEmpty()) return itm;
        itm = itm.copy();
        saveToItem(itm);
        return itm;
    }
}
