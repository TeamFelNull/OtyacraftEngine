package red.felnull.otyacraftengine.fabric.mixin;

import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import red.felnull.otyacraftengine.fluid.FluidProperties;
import red.felnull.otyacraftengine.fluid.IIkisugibleFluid;
import red.felnull.otyacraftengine.util.IKSGFluidUtil;

@Mixin(Fluid.class)
public class FluidMixin implements IIkisugibleFluid {
    private FluidProperties properties;

    @Override
    public FluidProperties getProperties() {
        if (properties == null) {
            properties = IKSGFluidUtil.createVanillaFluidProperties((Fluid) (Object) this);
        }
        return properties;
    }
}
