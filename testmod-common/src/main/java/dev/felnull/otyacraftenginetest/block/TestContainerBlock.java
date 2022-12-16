package dev.felnull.otyacraftenginetest.block;


import dev.felnull.otyacraftengine.block.OEBaseEntityBlock;
import dev.felnull.otyacraftenginetest.blockentity.TestContainerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class TestContainerBlock extends OEBaseEntityBlock {
    public TestContainerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }


    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (!level.isClientSide()) {
            if (level.getBlockEntity(blockPos) instanceof TestContainerBlockEntity testContainerBlockEntity) {
                if (player.isCrouching()) {
                    player.displayClientMessage(Component.literal("Server"), false);
                    testContainerBlockEntity.getItems().forEach(n -> player.displayClientMessage(n.getDisplayName(), false));
                } else {
                    if (!player.getMainHandItem().isEmpty())
                        testContainerBlockEntity.setItem(0, player.getMainHandItem().copy());
                }
            }
        } else {
            if (level.getBlockEntity(blockPos) instanceof TestContainerBlockEntity testContainerBlockEntity) {
                if (player.isCrouching()) {
                    player.displayClientMessage(Component.literal("Client"), false);
                    testContainerBlockEntity.getItems().forEach(n -> player.displayClientMessage(n.getDisplayName(), false));
                }
            }
        }
        return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new TestContainerBlockEntity(blockPos, blockState);
    }
}
