package red.felnull.otyacraftengine.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import red.felnull.otyacraftengine.item.storage.IContainerFluidTankItem;

public class TestTankBlockItem extends BlockItem implements IContainerFluidTankItem {
    public TestTankBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public int getPriorityFluidTankNumber() {
        return 0;
    }

    @Override
    public int getFluidTankCont() {
        return 1;
    }

    @Override
    public ItemStack getEmptyFluidTankItem() {
        return new ItemStack(this);
    }

    @Override
    public int getCapacity(ItemStack stack) {
        return 114514;
    }
}
