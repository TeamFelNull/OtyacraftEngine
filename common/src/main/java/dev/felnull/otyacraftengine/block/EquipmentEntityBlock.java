package dev.felnull.otyacraftengine.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public abstract class EquipmentEntityBlock extends OEBaseEntityBlock {
    protected EquipmentEntityBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (isOpenContainer(blockState, level, blockPos, player, interactionHand, blockHitResult)) {
            if (level.isClientSide()) {
                return InteractionResult.SUCCESS;
            } else {
                openContainer(blockState, (ServerLevel) level, blockPos, (ServerPlayer) player, interactionHand, blockHitResult);
                return InteractionResult.CONSUME;
            }
        }
        return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
    }

    protected boolean isOpenContainer(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        return level.getBlockEntity(blockPos) instanceof BaseContainerBlockEntity oeBaseContainerBlock;
    }

    protected void openContainer(BlockState blockState, ServerLevel level, BlockPos blockPos, ServerPlayer player, InteractionHand interactionHand, BlockHitResult blockHitResult) {

    }
}
