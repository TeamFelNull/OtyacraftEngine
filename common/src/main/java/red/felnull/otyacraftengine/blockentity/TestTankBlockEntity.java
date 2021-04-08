package red.felnull.otyacraftengine.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import red.felnull.otyacraftengine.blockentity.container.IkisugiFluidContainerBlockEntity;
import red.felnull.otyacraftengine.fluid.storage.FluidTank;

public class TestTankBlockEntity extends IkisugiFluidContainerBlockEntity {

    public TestTankBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(TestBlockEntity.TEST_TANK_BLOCKENTITY, blockPos, blockState);
    }

    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("tank.test.be");
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return null;
    }


    @Override
    public void createTanks(NonNullList<FluidTank> nonNullList) {
        nonNullList.set(0, FluidTank.createEmpty(114514));
    }


    @Override
    public int getFluidTankCount() {
        return 1;
    }
}
