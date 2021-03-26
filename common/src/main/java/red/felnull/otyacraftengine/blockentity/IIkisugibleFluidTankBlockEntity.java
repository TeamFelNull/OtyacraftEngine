package red.felnull.otyacraftengine.blockentity;

import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import red.felnull.otyacraftengine.fluid.IkisugiFluidTank;

import java.util.Optional;

public interface IIkisugibleFluidTankBlockEntity {
    Optional<IkisugiFluidTank> getFluidTank(Direction side);

    NonNullList<IkisugiFluidTank> getFluidTanks();

    IkisugiFluidTank getFluidTank(int number);

    boolean isAllFluidEmpty();

    int getFluidTankSize();
}