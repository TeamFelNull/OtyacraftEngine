package red.felnull.otyacraftengine.blockentity.container;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import red.felnull.otyacraftengine.blockentity.storage.IFluidTankBlockEntity;
import red.felnull.otyacraftengine.fluid.storage.FluidTank;
import red.felnull.otyacraftengine.util.IKSGContainerUtil;

import java.util.Optional;

public abstract class IkisugiFluidContainerBlockEntity extends IkisugiContainerBlockEntity implements IFluidTankBlockEntity {
    private final NonNullList<FluidTank> tanks;

    protected IkisugiFluidContainerBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
        NonNullList<FluidTank> tank = NonNullList.withSize(getFluidTankCount(), FluidTank.createEmpty());
        createTanks(tank);
        tanks = tank;
    }

    @Override
    public int getContainerSize() {
        return 0;
    }

    @Override
    public NonNullList<FluidTank> getFluidTanks() {
        return tanks;
    }

    @Override
    public boolean isFluidEmpty() {
        return this.getFluidTanks().stream().allMatch(FluidTank::isEmpty);
    }


    @Override
    public Optional<FluidTank> getFluidTank(Direction side) {
        return getDefaltFluid();
    }

    @Override
    public Optional<FluidTank> getDefaltFluid() {
        if (tanks.size() <= 0)
            return Optional.empty();
        return Optional.of(tanks.get(0));
    }

    @Override
    public FluidTank getFluidTank(int number) {
        return tanks.get(number);
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag) {
        super.save(compoundTag);
        IKSGContainerUtil.saveAllTanks(compoundTag, getFluidTanks());
        return compoundTag;
    }

    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        IKSGContainerUtil.loadAllTanks(compoundTag, getFluidTanks());
    }

    abstract public void createTanks(NonNullList<FluidTank> nonNullList);

    @Override
    public CompoundTag saveToTag(CompoundTag tag) {
        IKSGContainerUtil.saveAllTanks(tag, getFluidTanks(), false);
        return super.saveToTag(tag);
    }

    @Override
    public boolean isAllEmpty() {
        return isFluidEmpty();
    }

    @Override
    public CompoundTag clientSyncbleData(CompoundTag compoundTag) {
        IKSGContainerUtil.saveAllTanks(compoundTag, getFluidTanks());
        return compoundTag;
    }

    @Override
    public void clientSyncble(CompoundTag compoundTag) {
        getFluidTanks().clear();
        createTanks(getFluidTanks());
        IKSGContainerUtil.loadAllTanks(compoundTag, getFluidTanks());
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getItem(int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItem(int i, int j) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItemNoUpdate(int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public void clearContent() {
        getFluidTanks().clear();
        createTanks(getFluidTanks());
    }

    @Override
    public boolean stillValid(Player player) {
        return isUsableByPlayer(player);
    }

    @Override
    public void setItem(int i, ItemStack itemStack) {

    }

}
