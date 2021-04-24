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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import red.felnull.otyacraftengine.block.IkisugiLiquidBlock;
import red.felnull.otyacraftengine.item.IkisugiBucketItem;

import java.util.Optional;

public class IkisugiFluid extends FlowingFluid implements IIkisugibleFluid {
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

    @Override
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
        return properties.isCanMultiply();
    }

    @Override
    protected void beforeDestroyingBlock(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {
        BlockEntity blockEntity = blockState.hasBlockEntity() ? levelAccessor.getBlockEntity(blockPos) : null;
        Block.dropResources(blockState, levelAccessor, blockPos, blockEntity);
    }

    @Override
    protected int getSlopeFindDistance(LevelReader levelReader) {
        return properties.getSlopeFindDistance();
    }

    @Override
    protected int getDropOff(LevelReader levelReader) {
        return properties.getLevelDecreasePerBlock();
    }

    @Override
    public Item getBucket() {
        return data.getBucketItem();
    }

    @Override
    protected boolean canBeReplacedWith(FluidState fluidState, BlockGetter blockGetter, BlockPos blockPos, Fluid fluid, Direction direction) {
        return direction == Direction.DOWN && !isSame(fluid);
    }

    @Override
    public int getTickDelay(LevelReader levelReader) {
        return properties.getTickDelay();
    }

    @Override
    protected float getExplosionResistance() {
        return properties.getExplosionResistance();
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

    public Item createBucketItem(Item.Properties propertiesitm) {
        return new IkisugiBucketItem(this, propertiesitm.craftRemainder(Items.BUCKET).stacksTo(1).rarity(properties.getRarity()));
    }

    public Block createLiquidBlock(BlockBehaviour.Properties propertiesblc) {

        if (getProperties().getLightLevel() > 0) {
            propertiesblc.lightLevel(n -> getProperties().getLightLevel());
        }

        return new IkisugiLiquidBlock(this, propertiesblc.noCollission().noDrops().noCollission().strength(100.0F));
    }

    @Override
    public Optional<SoundEvent> getPickupSound() {
        return Optional.of(properties.getPickupSound());
    }
}
