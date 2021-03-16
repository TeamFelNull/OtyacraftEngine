package red.felnull.otyacraftengine.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import red.felnull.otyacraftengine.fluid.IIkisugiFluidHandler;
import red.felnull.otyacraftengine.fluid.IkisugiFluidTank;

import java.util.Optional;

public class TestTankBlockEntity extends IkisugiBlockEntity implements IIkisugibleTankBlockEntity {

    private IkisugiFluidTank tank = new IkisugiFluidTank(10000);

    public TestTankBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(TestBlockEntity.TEST_TANK_BLOCKENTITY, blockPos, blockState);
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag) {
        tank.writeToNBT(compoundTag);
        return super.save(compoundTag);
    }

    @Override
    public void load(CompoundTag compoundTag) {
        tank = tank.readFromNBT(compoundTag);
        super.load(compoundTag);
    }


    @Override
    public Optional<IIkisugiFluidHandler> getFluidCapability() {
        return Optional.of(tank);
    }
}
