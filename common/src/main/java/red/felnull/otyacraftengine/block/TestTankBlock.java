package red.felnull.otyacraftengine.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
        ItemStack stack = player.getItemInHand(interactionHand);
        if (player.isShiftKeyDown()) {
            if (!level.isClientSide()) {
                if (level.getBlockEntity(blockPos) instanceof TestTankBlockEntity) {
                    TestTankBlockEntity ttbe = (TestTankBlockEntity) level.getBlockEntity(blockPos);
                    player.displayClientMessage(new TextComponent("Fluid: ").append(ttbe.getTank().getFluidStack().getName()), false);
                    player.displayClientMessage(new TextComponent("Amont: " + ttbe.getTank().getFluidStack().getAmount().intValue() + "mb").append("/").append(ttbe.getTank().getCapacity() + "mb"), false);
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide());
        } else {
            if (IKSGFluidUtil.interactWithFluidTank(player, interactionHand, level, blockPos, blockHitResult.getDirection())) {
                return InteractionResult.sidedSuccess(level.isClientSide());
            }
            //    player.displayClientMessage(new TextComponent("test: " + (stack.getItem() instanceof IIkisugibleFluidTankItem)), false);
/*
            if (stack.getItem() instanceof IIkisugibleFluidTankItem && level.getBlockEntity(blockPos) instanceof TestTankBlockEntity) {
                Optional<IkisugiFluidTank> tank = ((IIkisugibleFluidTankItem) stack.getItem()).getFluidTank(stack);
                tank.ifPresent(n->{
                    player.displayClientMessage(new TextComponent("Fluid: ").append(n.getFluidStack().getName()), false);
                    player.displayClientMessage(new TextComponent("Amont: " + n.getFluidStack().getAmount().intValue() + "mb"), false);
                    player.displayClientMessage(new TextComponent("Capacity: " + n.getCapacity() + "mb"), false);
                });
            }*/
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }
}
