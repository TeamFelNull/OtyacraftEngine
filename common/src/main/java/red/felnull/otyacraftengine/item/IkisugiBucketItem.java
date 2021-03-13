package red.felnull.otyacraftengine.item;

import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.material.Fluid;
import red.felnull.otyacraftengine.fluid.IkisugiFluid;

public class IkisugiBucketItem extends BucketItem {
    private final Fluid content;

    public IkisugiBucketItem(Fluid fluid, Properties properties) {
        super(fluid, properties);
        this.content = fluid;
    }

    public int getFluidColor() {
        if (content instanceof IkisugiFluid) {
            return ((IkisugiFluid) content).getProperties().getColor();
        }
        return -1;
    }

    public boolean isColoring() {
        if (content instanceof IkisugiFluid) {
            return ((IkisugiFluid) content).getProperties().isColoring();
        }
        return false;
    }

    public Fluid getFluid() {
        return content;
    }
}
