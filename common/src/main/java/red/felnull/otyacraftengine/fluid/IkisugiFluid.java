package red.felnull.otyacraftengine.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

import java.util.function.Supplier;

public class IkisugiFluid extends FlowingFluid {
    private final FluidProperties properties;
    private final Supplier<Fluid> sourceFluid;
    private final Supplier<FlowingFluid> flowingFluid;
    private final Supplier<Item> bucket;
    private final Supplier<Block> liquidBlock;
    private final boolean source;

    public IkisugiFluid(FluidProperties properties, Supplier<Fluid> sourceFluid, Supplier<FlowingFluid> flowingFluid, Supplier<Item> bucket, Supplier<Block> liquidBlock, boolean source) {
        this.properties = properties;
        this.sourceFluid = sourceFluid;
        this.flowingFluid = flowingFluid;
        this.bucket = bucket;
        this.liquidBlock = liquidBlock;
        this.source = source;
    }

    public FluidProperties getProperties() {
        return properties;
    }

    @Override
    public Fluid getFlowing() {
        return flowingFluid.get();
    }

    @Override
    public Fluid getSource() {
        return sourceFluid.get();
    }

    @Override
    protected boolean canConvertToSource() {
        return false;
    }

    @Override
    protected void beforeDestroyingBlock(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {

    }

    @Override
    protected int getSlopeFindDistance(LevelReader levelReader) {
        return 4;
    }

    @Override
    protected int getDropOff(LevelReader levelReader) {
        return 1;
    }

    @Override
    public Item getBucket() {
        return bucket.get();
    }

    @Override
    protected boolean canBeReplacedWith(FluidState fluidState, BlockGetter blockGetter, BlockPos blockPos, Fluid fluid, Direction direction) {
        return false;
    }

    @Override
    public int getTickDelay(LevelReader levelReader) {
        return 10;
    }

    @Override
    protected float getExplosionResistance() {
        return 100f;
    }

    @Override
    protected BlockState createLegacyBlock(FluidState fluidState) {
        return liquidBlock.get().defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(fluidState));
    }

    @Override
    public boolean isSource(FluidState fluidState) {
        return source;
    }

    @Override
    public int getAmount(FluidState fluidState) {
        return source ? 8 : fluidState.getValue(LEVEL);
    }

    @Override
    protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
        super.createFluidStateDefinition(builder);
        if (!source) {
            builder.add(LEVEL);
        }
    }

    @Override
    public boolean isSame(Fluid fluid) {
        return getFlowing() == fluid || getSource() == fluid;
    }
}
