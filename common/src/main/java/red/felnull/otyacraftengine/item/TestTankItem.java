package red.felnull.otyacraftengine.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class TestTankItem extends Item implements IIkisugibleFluidTankItem {
    public TestTankItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack getEmptyFluidItem() {
        return new ItemStack(this);
    }

    @Override
    public int getCapacity() {
        return 19419;
    }
}
