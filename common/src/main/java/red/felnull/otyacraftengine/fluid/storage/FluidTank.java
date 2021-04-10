package red.felnull.otyacraftengine.fluid.storage;

import me.shedaniel.architectury.fluid.FluidStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.material.Fluid;
import red.felnull.otyacraftengine.util.IKSGMath;

import java.util.function.Function;

public class FluidTank {
    private long capacity;
    private FluidStack fluid = FluidStack.empty();
    private Function<FluidStack, Boolean> filter = null;

    private FluidTank(long capacity, Function<FluidStack, Boolean> filter) {
        this.capacity = capacity;
        this.filter = filter;
    }

    public static FluidTank loadTank(CompoundTag tag, long capacity) {
        return loadTank(tag, capacity, null);
    }

    public static FluidTank loadTank(CompoundTag tag, long capacity, Function<FluidStack, Boolean> filter) {
        FluidTank tank = createEmpty(capacity, filter);
        tank.load(tag);
        return tank;
    }

    public static FluidTank createEmpty(long capacity, Function<FluidStack, Boolean> filter) {
        return new FluidTank(capacity, filter);
    }

    public static FluidTank createEmpty(long capacity) {
        return new FluidTank(capacity, null);
    }

    public static FluidTank createEmpty() {
        return new FluidTank(0, null);
    }

    public FluidStack getFluidStack() {
        return fluid;
    }

    public void setFluidStack(FluidStack fluid) {
        this.fluid = fluid;
    }

    public long getCapacity() {
        return capacity;
    }

    public void setCapacity(long capacity) {
        this.capacity = capacity;
    }

    public Function<FluidStack, Boolean> getFilter() {
        return filter;
    }

    public CompoundTag save(CompoundTag compoundTag) {
        compoundTag.put("Fluid", fluid.write(new CompoundTag()));
        return compoundTag;
    }

    public void setFilter(Function<FluidStack, Boolean> filter) {
        this.filter = filter;
    }

    public void load(CompoundTag compoundTag) {
        fluid = FluidStack.read(compoundTag.getCompound("Fluid"));
    }

    public long getAmount() {
        return fluid.getAmount();
    }

    public Fluid getFluid() {
        return fluid.getFluid();
    }

    public long addFluidStack(FluidStack stack) {

        if (stack.isEmpty())
            return 0;

        if (getFluidStack().isEmpty() || getFluid() == stack.getRawFluid()) {
            setFluid(stack.getFluid());
            return addAmount(stack.getAmount());
        }
        return stack.getAmount();
    }

    /**
     * 液体の量を減らす
     *
     * @param value 減らす量
     * @return 余った量
     */
    public long reduceAmount(long value) {
        long allAmont = getAmount() - value;
        setAmount(allAmont);
        return Math.max(-allAmont, 0);
    }

    /**
     * 実際には液体量を減らさずに余った量を予測する
     *
     * @param value 減らす量
     * @return 余った量予想
     */
    public long simulateReduceAmount(long value) {
        long allAmont = getAmount() - value;
        return Math.max(-allAmont, 0);
    }

    /**
     * 液体の量を追加する
     *
     * @param value 追加する量
     * @return 余った量
     */
    public long addAmount(long value) {
        long allAmont = getAmount() + value;
        setAmount(allAmont);
        return Math.max(allAmont - capacity, 0);
    }


    /**
     * 実際には液体量を追加せずに余った量を予測する
     *
     * @param value 追加する量
     * @return 余った量の予測
     */
    public long simulateAddAmount(long value) {
        long allAmont = getAmount() + value;
        return Math.max(allAmont - capacity, 0);
    }

    public long simulateAddFluidStack(FluidStack stack) {
        if (stack.isEmpty())
            return 0;
        if (getFluidStack().isEmpty() || getFluid() == stack.getRawFluid()) {
            return simulateAddAmount(stack.getAmount());
        }
        return stack.getAmount();
    }

    public void setAmount(long value) {
        FluidStack stack = fluid.copy();
        stack.setAmount(IKSGMath.clamp(value, 0, capacity));
        setFluidStack(stack);
        update(false);
    }


    public void setFluid(Fluid fluid) {
        setFluidStack(FluidStack.create(fluid, getAmount()));
        update(true);
    }

    public void update(boolean chaneFluid) {
        if (capacity < getAmount()) {
            FluidStack stack = fluid.copy();
            stack.setAmount(capacity);
            setFluidStack(stack);
        }
        if (!chaneFluid) {
            if (getAmount() <= 0) {
                setFluidStack(FluidStack.empty());
            }
        }
    }

    public boolean isMaxCapacity() {
        return capacity <= getAmount();
    }

    public boolean isEmpty() {
        return fluid.isEmpty() || getAmount() <= 0;
    }

    public double getAmountPercent() {
        return (double) getAmount() / (double) capacity;
    }

    public boolean canAddFluid(FluidStack fluid) {
        return filter == null || filter.apply(fluid);
    }

}
