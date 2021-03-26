package red.felnull.otyacraftengine.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import red.felnull.otyacraftengine.fluid.IkisugiFluidTank;

import java.util.Optional;

public class TestTankBlockEntity extends IkisugiContainerBlockEntity implements IIkisugibleFluidTankBlockEntity {

    public TestTankBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(TestBlockEntity.TEST_TANK_BLOCKENTITY, blockPos, blockState);
        setFluidTankCapacity(0, 114514);
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
    public Optional<IkisugiFluidTank> getFluidTank(Direction side) {
        return Optional.of(getFluidTank(0));
    }

    @Override
    public int getFluidTankSize() {
        return 1;
    }

}
