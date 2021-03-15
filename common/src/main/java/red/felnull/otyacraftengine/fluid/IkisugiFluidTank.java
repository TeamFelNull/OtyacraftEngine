package red.felnull.otyacraftengine.fluid;

import me.shedaniel.architectury.fluid.FluidStack;
import me.shedaniel.architectury.utils.Fraction;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class IkisugiFluidTank implements IIkisugiFluidHandler, IIkisugiFluidTank {
    protected Predicate<FluidStack> validator;
    @Nullable
    protected FluidStack fluid = FluidStack.empty();
    protected int capacity;

    public IkisugiFluidTank(int capacity) {
        this(capacity, e -> true);
    }

    public IkisugiFluidTank(int capacity, Predicate<FluidStack> validator) {
        this.capacity = capacity;
        this.validator = validator;
    }

    public IkisugiFluidTank setCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public IkisugiFluidTank setValidator(Predicate<FluidStack> validator) {
        if (validator != null) {
            this.validator = validator;
        }
        return this;
    }

    public boolean isFluidValid(FluidStack stack) {
        return validator.test(stack);
    }

    public int getCapacity() {
        return capacity;
    }

    @Nullable
    public FluidStack getFluid() {
        return fluid;
    }

    public int getFluidAmount() {
        return fluid.getAmount().intValue();
    }

    public IkisugiFluidTank readFromNBT(CompoundTag nbt) {

        FluidStack fluid = FluidStack.read(nbt);
        setFluid(fluid);
        return this;
    }

    public CompoundTag writeToNBT(CompoundTag nbt) {

        fluid.write(nbt);

        return nbt;
    }

    @Override
    public int getTanks() {

        return 1;
    }

    @Nullable
    @Override
    public FluidStack getFluidInTank(int tank) {

        return getFluid();
    }

    @Override
    public int getTankCapacity(int tank) {

        return getCapacity();
    }

    @Override
    public boolean isFluidValid(int tank, @Nullable FluidStack stack) {

        return isFluidValid(stack);
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        if (resource.isEmpty() || !isFluidValid(resource)) {
            return 0;
        }
        if (action.simulate()) {
            if (fluid.isEmpty()) {
                return Math.min(capacity, resource.getAmount().intValue());
            }
            if (!fluid.isFluidStackEqual(resource)) {
                return 0;
            }
            return Math.min(capacity - fluid.getAmount().intValue(), resource.getAmount().intValue());
        }
        if (fluid.isEmpty()) {
            fluid = FluidStack.create(resource, Fraction.ofWhole(Math.min(capacity, resource.getAmount().intValue())));
            onContentsChanged();
            return fluid.getAmount().intValue();
        }
        if (!fluid.isFluidStackEqual(resource)) {
            return 0;
        }
        int filled = capacity - fluid.getAmount().intValue();

        if (resource.getAmount().intValue() < filled) {
            fluid.grow(resource.getAmount());
            filled = resource.getAmount().intValue();
        } else {
            fluid.setAmount(Fraction.ofWhole(capacity));
        }
        if (filled > 0)
            onContentsChanged();
        return filled;
    }

    @Nullable
    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        if (resource.isEmpty() || !resource.isFluidStackEqual(fluid)) {
            return FluidStack.empty();
        }
        return drain(resource.getAmount().intValue(), action);
    }

    @Nullable
    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        int drained = maxDrain;
        if (fluid.getAmount().intValue() < drained) {
            drained = fluid.getAmount().intValue();
        }
        FluidStack stack = FluidStack.create(fluid, Fraction.ofWhole(drained));
        if (action.execute() && drained > 0) {
            fluid.shrink(Fraction.ofWhole(drained));
            onContentsChanged();
        }
        return stack;
    }

    protected void onContentsChanged() {

    }

    public void setFluid(FluidStack stack) {
        this.fluid = stack;
    }

    public boolean isEmpty() {
        return fluid.isEmpty();
    }

    public int getSpace() {
        return Math.max(0, capacity - fluid.getAmount().intValue());
    }

}
