package dev.felnull.otyacraftengine.block;

import dev.felnull.otyacraftengine.blockentity.ItemDroppedBlockEntity;
import dev.felnull.otyacraftengine.blockentity.OEBaseContainerBlockEntity;
import dev.felnull.otyacraftengine.util.OEMenuUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class OEBaseEntityBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final ResourceLocation CONTENTS = new ResourceLocation("contents");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private boolean analogOutput;

    protected OEBaseEntityBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
    }

    @Override
    public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity livingEntity, ItemStack itemStack) {
        if (itemStack.hasCustomHoverName()) {
            if (level.getBlockEntity(blockPos) instanceof BaseContainerBlockEntity container) {
                container.setCustomName(itemStack.getHoverName());
            }
        }
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!blockState.is(blockState2.getBlock())) {
            var be = level.getBlockEntity(blockPos);
            if (be instanceof Container container) {
                if (level instanceof ServerLevel) {
                    Containers.dropContents(level, blockPos, container);
                }
                if (analogOutput)
                    level.updateNeighbourForOutputSignal(blockPos, this);
            }
            if (be instanceof ItemDroppedBlockEntity itemDroppedBlock) {
                if (!itemDroppedBlock.isRetainDrop())
                    Containers.dropContents(level, blockPos, itemDroppedBlock.getDroppedItems());
            }
        }
        super.onRemove(blockState, level, blockPos, blockState2, bl);
    }


    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        } else {
            this.openContainer(level, blockPos, (ServerPlayer) player, blockState, blockHitResult.getDirection());
            return InteractionResult.CONSUME;
        }
    }

    protected void openContainer(Level level, BlockPos blockPos, ServerPlayer player, BlockState blockState, Direction direction) {
        if (level.getBlockEntity(blockPos) instanceof OEBaseContainerBlockEntity oeBaseContainerBlock)
            OEMenuUtil.openMenu(player, oeBaseContainerBlock, blockPos, oeBaseContainerBlock.getContainerSize());
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState blockState) {
        return analogOutput;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos blockPos) {
        if (!analogOutput)
            return super.getAnalogOutputSignal(blockState, level, blockPos);
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(blockPos));
    }

    public void setAnalogOutput(boolean enable) {
        this.analogOutput = enable;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return !blockState.getValue(WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState blockState) {
        return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
    }

    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        if (blockState.getValue(WATERLOGGED)) {
            levelAccessor.getLiquidTicks().scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        }
        return super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        FluidState fluidState = blockPlaceContext.getLevel().getFluidState(blockPlaceContext.getClickedPos());
        return super.getStateForPlacement(blockPlaceContext).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Override
    public List<ItemStack> getDrops(BlockState blockState, LootContext.Builder builder) {
        BlockEntity blockEntity = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (blockEntity instanceof ItemDroppedBlockEntity icbe) {
            if (icbe.isRetainDrop()) {
                builder = builder.withDynamicDrop(CONTENTS, (lootContext, consumer) -> {
                    for (int i = 0; i < icbe.getDroppedItems().size(); ++i) {
                        consumer.accept(icbe.getDroppedItems().get(i));
                    }
                });
            }
        }
        return super.getDrops(blockState, builder);
    }
}
