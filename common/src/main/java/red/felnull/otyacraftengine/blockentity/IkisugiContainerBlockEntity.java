package red.felnull.otyacraftengine.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import red.felnull.otyacraftengine.fluid.IkisugiFluidTank;
import red.felnull.otyacraftengine.util.IKSGContainerUtil;

public abstract class IkisugiContainerBlockEntity extends BaseContainerBlockEntity implements IIkisugibleBlockEntity {

    private final NonNullList<ItemStack> items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);

    protected IkisugiContainerBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    public NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    public BlockEntity getBlockEntity() {
        return this;
    }

    @Override
    public boolean isEmpty() {
        return this.getItems().stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public ItemStack getItem(int i) {
        return this.getItems().get(i);
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
    }


    @Override
    public boolean stillValid(Player player) {
        return isUsableByPlayer(player);
    }

    @Override
    public void clearContent() {
        getItems().clear();
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag) {
        super.save(compoundTag);
        ContainerHelper.saveAllItems(compoundTag, getItems());
        if (isTank()) {
            IKSGContainerUtil.saveAllTanks(compoundTag, ((IIkisugibleFluidTankBlockEntity) this).getFluidTanks());
        }
        return compoundTag;
    }

    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        ContainerHelper.loadAllItems(compoundTag, getItems());
        if (isTank()) {
            IKSGContainerUtil.loadAllTanks(compoundTag, ((IIkisugibleFluidTankBlockEntity) this).getFluidTanks());
        }
    }

    public CompoundTag saveToTag(CompoundTag tag) {
        ContainerHelper.saveAllItems(tag, getItems(), false);
        if (isTank()) {
            IKSGContainerUtil.saveAllTanks(tag, ((IIkisugibleFluidTankBlockEntity) this).getFluidTanks(), false);
        }
        return tag;
    }

    public boolean isAllEmpty() {
        boolean flag1 = isEmpty();
        boolean flag2 = true;
        if (isTank()) {
            flag2 = ((IIkisugibleFluidTankBlockEntity) this).getFluidTanks().stream().allMatch(IkisugiFluidTank::isEmpty);
        }
        return flag1 && flag2;
    }

    public boolean isTank() {
        return this instanceof IIkisugibleFluidTankBlockEntity;
    }
}
