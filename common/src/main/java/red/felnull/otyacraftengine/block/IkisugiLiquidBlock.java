package red.felnull.otyacraftengine.block;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.LiquidBlock;
import red.felnull.otyacraftengine.fluid.IkisugiFluid;

public class IkisugiLiquidBlock extends LiquidBlock implements IIkisugibleBlock {
    protected final IkisugiFluid fluid;

    public IkisugiLiquidBlock(IkisugiFluid flowingFluid, Properties properties) {
        super(flowingFluid, properties);
        this.fluid = flowingFluid;
    }

    public IkisugiFluid getFluid() {
        return fluid;
    }

    @Override
    public ResourceLocation getRegistryName() {
        return Registry.BLOCK.getKey(this);
    }
}
