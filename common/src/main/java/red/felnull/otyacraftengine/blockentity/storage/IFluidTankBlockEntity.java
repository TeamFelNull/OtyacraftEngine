package red.felnull.otyacraftengine.blockentity.storage;

import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import red.felnull.otyacraftengine.fluid.storage.FluidTank;

import java.util.Optional;

public interface IFluidTankBlockEntity {
    Optional<FluidTank> getFluidTank(Direction side);

    NonNullList<FluidTank> getFluidTanks();

    FluidTank getFluidTank(int number);

    boolean isFluidEmpty();

    int getFluidTankCount();

    Optional<FluidTank> getDefaltFluid();
}
