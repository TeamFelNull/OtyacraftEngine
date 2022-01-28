package dev.felnull.otyacraftengine.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class OEBaseContainerBlockEntity extends BaseContainerBlockEntity implements IClientSyncbleBlockEntity, IInstructionBlockEntity, ItemDroppedBlockEntity {

    protected OEBaseContainerBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    @Override
    public CompoundTag getSyncData(ServerPlayer player, CompoundTag tag) {
        if (getItems() != null) {
            ContainerHelper.saveAllItems(tag, getItems());
            return tag;
        }
        return null;
    }

    @Override
    public void onSync(CompoundTag tag) {
        if (getItems() != null)
            ContainerHelper.loadAllItems(tag, getItems());
    }

    @Override
    public void sync() {
        IClientSyncbleBlockEntity.syncBlockEntity(this);
    }

    @Override
    public CompoundTag onInstruction(ServerPlayer player, String name, int num, CompoundTag data) {
        return null;
    }

    @Override
    public boolean canInstructionWith(ServerPlayer player, String name, int num, CompoundTag data) {
        return stillValid(player);
    }

    abstract public NonNullList<ItemStack> getItems();

    @Override
    public int getContainerSize() {
        if (getItems() == null)
            return 0;
        return getItems().size();
    }

    @Override
    public boolean isEmpty() {
        if (getItems() == null)
            return true;
        return getItems().stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public ItemStack getItem(int i) {
        if (getItems() == null)
            return null;
        return getItems().get(i);
    }

    @Override
    public ItemStack removeItem(int i, int j) {
        if (getItems() == null)
            return null;
        return ContainerHelper.removeItem(getItems(), i, j);
    }

    @Override
    public ItemStack removeItemNoUpdate(int i) {
        if (getItems() == null)
            return null;
        return ContainerHelper.takeItem(getItems(), i);
    }

    @Override
    public void setItem(int i, ItemStack stack) {
        if (getItems() == null)
            return;
        ItemStack itemstack = getItems().get(i);
        boolean flag = !stack.isEmpty() && stack.sameItem(itemstack) && ItemStack.tagMatches(stack, itemstack);
        getItems().set(i, stack);
        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }
        if (flag)
            this.setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        if (this.level.getBlockEntity(this.worldPosition) != this)
            return false;
        return isUsableByPlayer(player);
    }

    @Override
    public void clearContent() {
        if (getItems() == null)
            return;
        getItems().clear();
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (getItems() != null)
            ContainerHelper.loadAllItems(tag, getItems());
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        if (getItems() != null)
            ContainerHelper.saveAllItems(tag, getItems());
        return super.save(tag);
    }

    public boolean isUsableByPlayer(Player player) {
        return getLevel().getBlockEntity(getBlockPos()) == this && player.distanceToSqr((double) getBlockPos().getX() + 0.5D, (double) getBlockPos().getY() + 0.5D, (double) getBlockPos().getZ() + 0.5D) <= 64.0D;
    }

    @Override
    public NonNullList<ItemStack> getDroppedItems() {
        if (getItems() == null)
            return null;
        return getItems();
    }
}
