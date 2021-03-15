package red.felnull.otyacraftengine.fluid;

import me.shedaniel.architectury.fluid.FluidStack;

public interface IIkisugiFluidTank {
    FluidStack getFluid();

    int getFluidAmount();

    int getCapacity();

    boolean isFluidValid(FluidStack stack);

    int fill(FluidStack resource, IIkisugiFluidHandler.FluidAction action);

    FluidStack drain(int maxDrain, IIkisugiFluidHandler.FluidAction action);

    FluidStack drain(FluidStack resource, IIkisugiFluidHandler.FluidAction action);
}
