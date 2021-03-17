package red.felnull.otyacraftengine.blockentity;

import net.minecraft.core.Direction;
import red.felnull.otyacraftengine.fluid.IkisugiFluidTank;

import java.util.Optional;

public interface IIkisugibleTankBlockEntity {
    Optional<IkisugiFluidTank> getTank(Direction side);
}
