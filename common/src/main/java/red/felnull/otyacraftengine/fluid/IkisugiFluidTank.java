package red.felnull.otyacraftengine.fluid;

import me.shedaniel.architectury.fluid.FluidStack;
import me.shedaniel.architectury.utils.Fraction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.material.Fluid;

public class IkisugiFluidTank {
    private int capacity;
    private FluidStack fluid = FluidStack.empty();

    public IkisugiFluidTank(int capacity) {
        this.capacity = capacity;
    }

    public FluidStack getFluidStack() {
        return fluid;
    }

    public void setFluidStack(FluidStack fluid) {
        this.fluid = fluid;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public CompoundTag save(CompoundTag compoundTag) {
        compoundTag.put("Fluid", fluid.write(new CompoundTag()));
        return compoundTag;
    }

    public void load(CompoundTag compoundTag) {
        fluid = FluidStack.read(compoundTag.getCompound("Fluid"));
    }

    public int getAmount() {
        return fluid.getAmount().intValue();
    }

    public Fluid getFluid() {
        return fluid.getFluid();
    }

    public int addFluidStack(FluidStack stack) {

        if (stack.isEmpty())
            return 0;

        if (getFluidStack().isEmpty() || getFluid() == stack.getRawFluid()) {
            setFluid(stack.getFluid());
            return addAmount(stack.getAmount().intValue());
        }
        return stack.getAmount().intValue();
    }

    /**
     * 液体の量を減らす
     *
     * @param value 減らす量
     * @return 余った量
     */
    public int reduceAmount(int value) {
        int allAmont = getAmount() - value;
        setAmount(allAmont);
        return Math.max(-allAmont, 0);
    }

    /**
     * 実際には液体量を減らさずに余った量を予測する
     *
     * @param value 減らす量
     * @return 余った量予想
     */
    public int simulateReduceAmount(int value) {
        int allAmont = getAmount() - value;
        return Math.max(-allAmont, 0);
    }

    /**
     * 液体の量を追加する
     *
     * @param value 追加する量
     * @return 余った量
     */
    public int addAmount(int value) {
        int allAmont = getAmount() + value;
        setAmount(allAmont);
        return Math.max(allAmont - capacity, 0);
    }


    /**
     * 実際には液体量を追加せずに余った量を予測する
     *
     * @param value 追加する量
     * @return 余った量の予測
     */
    public int simulateAddAmount(int value) {
        int allAmont = getAmount() + value;
        return Math.max(allAmont - capacity, 0);
    }

    public int simulateAddFluidStack(FluidStack stack) {
        if (stack.isEmpty())
            return 0;
        if (getFluidStack().isEmpty() || getFluid() == stack.getRawFluid()) {
            return simulateAddAmount(stack.getAmount().intValue());
        }
        return stack.getAmount().intValue();
    }

    public void setAmount(int value) {
        FluidStack stack = fluid.copy();
        stack.setAmount(Fraction.ofWhole(Math.min(Math.max(value, 0), capacity)));
        setFluidStack(stack);
        update(false);
    }


    public void setFluid(Fluid fluid) {
        setFluidStack(FluidStack.create(fluid, Fraction.ofWhole(getAmount())));
        update(true);
    }

    public void update(boolean chaneFluid) {
        if (capacity < getAmount()) {
            FluidStack stack = fluid.copy();
            stack.setAmount(Fraction.ofWhole(capacity));
            setFluidStack(stack);
        }
        if (!chaneFluid) {
            if (getAmount() == 0) {
                setFluidStack(FluidStack.empty());
            }
        }
    }

    public boolean isMaxCapacity() {
        return capacity <= getAmount();
    }

}
