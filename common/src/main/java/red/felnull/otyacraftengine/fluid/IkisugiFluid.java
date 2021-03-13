package red.felnull.otyacraftengine.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import red.felnull.otyacraftengine.block.IkisugiLiquidBlock;
import red.felnull.otyacraftengine.item.IkisugiBucketItem;

import java.util.Optional;

public class IkisugiFluid extends FlowingFluid {
    private final FluidProperties properties;
    private final FluidData data;
    private final boolean source;

    public IkisugiFluid(FluidProperties properties, FluidData fluidData) {
        this(properties, fluidData, true);
    }

    public IkisugiFluid(FluidProperties properties, FluidData fluidData, boolean isSource) {
        this.properties = properties;
        this.data = fluidData;
        this.source = isSource;
    }

    public FluidProperties getProperties() {
        return properties;
    }

    public FluidData getData() {
        return data;
    }

    @Override
    public Fluid getFlowing() {
        return data.getFlowingFluid();
    }

    @Override
    public Fluid getSource() {
        return data.getSourceFluid();
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
        return data.getBucketItem();
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
        return data.getLiquidBlock().defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(fluidState));
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

    public FlowingFluid createFlowingFluid() {
        return new IkisugiFluid(properties, data, false);
    }

    public Item createBucketItem(Item.Properties properties) {
        return new IkisugiBucketItem(this, properties.craftRemainder(Items.BUCKET).stacksTo(1));
    }

    public Block createLiquidBlock(BlockBehaviour.Properties properties) {
        return new IkisugiLiquidBlock(this, properties.noCollission().noDrops().noCollission().strength(100.0F));
    }

    @Override
    public Optional<SoundEvent> getPickupSound() {
        return Optional.of(properties.getPickupSound());
    }
}
