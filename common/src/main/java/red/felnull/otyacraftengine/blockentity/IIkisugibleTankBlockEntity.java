package red.felnull.otyacraftengine.blockentity;

import red.felnull.otyacraftengine.fluid.IIkisugiFluidHandler;

import java.util.Optional;

public interface IIkisugibleTankBlockEntity {
    Optional<IIkisugiFluidHandler> getFluidCapability();
}
