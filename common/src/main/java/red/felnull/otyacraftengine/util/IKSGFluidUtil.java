package red.felnull.otyacraftengine.util;

import me.shedaniel.architectury.fluid.FluidStack;
import net.minecraft.world.item.ItemStack;
import red.felnull.otyacraftengine.impl.OEExpectPlatform;

import java.util.Optional;

public class IKSGFluidUtil {
    public static Optional<FluidStack> getFluidContained(ItemStack container) {
        if (!container.isEmpty()) {
            return OEExpectPlatform.getFluidContained(container);
        }
        return Optional.empty();
    }
}
