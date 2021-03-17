package red.felnull.otyacraftengine.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import red.felnull.otyacraftengine.fluid.IkisugiFluidTank;

import java.util.Optional;

public class TestTankBlockEntity extends IkisugiBlockEntity implements IIkisugibleTankBlockEntity {
    private IkisugiFluidTank tank;

    public TestTankBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(TestBlockEntity.TEST_TANK_BLOCKENTITY, blockPos, blockState);
        tank = new IkisugiFluidTank(10194);
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag) {
        compoundTag.put("Tank", tank.save(new CompoundTag()));
        return super.save(compoundTag);
    }

    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        tank.load(compoundTag.getCompound("Tank"));
    }

    public IkisugiFluidTank getTank() {
        return tank;
    }

    @Override
    public Optional<IkisugiFluidTank> getTank(Direction side) {
        return Optional.of(tank);
    }
}
