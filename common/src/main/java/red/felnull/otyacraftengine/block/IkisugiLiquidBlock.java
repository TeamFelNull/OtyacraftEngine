package red.felnull.otyacraftengine.block;

import net.minecraft.world.level.block.LiquidBlock;
import red.felnull.otyacraftengine.fluid.IkisugiFluid;

public class IkisugiLiquidBlock extends LiquidBlock {
    protected final IkisugiFluid fluid;

    public IkisugiLiquidBlock(IkisugiFluid flowingFluid, Properties properties) {
        super(flowingFluid, properties);
        this.fluid = flowingFluid;
    }

    public IkisugiFluid getFluid() {
        return fluid;
    }
}
