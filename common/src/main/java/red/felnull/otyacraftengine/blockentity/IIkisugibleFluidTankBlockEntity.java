package red.felnull.otyacraftengine.blockentity;

import net.minecraft.core.Direction;
import red.felnull.otyacraftengine.fluid.IkisugiFluidTank;

import java.util.Optional;

public interface IIkisugibleFluidTankBlockEntity {
    Optional<IkisugiFluidTank> getFluidTank(Direction side);
}
