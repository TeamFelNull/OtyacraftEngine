package red.felnull.otyacraftengine.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import red.felnull.otyacraftengine.blockentity.TestTankBlockEntity;
import red.felnull.otyacraftengine.util.IKSGFluidUtil;

public class TestTankBlock extends IkisugiBaseEntityBlock {
    protected TestTankBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new TestTankBlockEntity(blockPos, blockState);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (IKSGFluidUtil.interactWithFluidHandler(player, interactionHand, level, blockPos, blockHitResult.getDirection())) {
            return InteractionResult.sidedSuccess(level.isClientSide());
        }
        return InteractionResult.PASS;
    }
}
