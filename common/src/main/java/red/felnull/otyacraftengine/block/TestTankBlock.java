package red.felnull.otyacraftengine.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import red.felnull.otyacraftengine.blockentity.TestBlockEntity;
import red.felnull.otyacraftengine.blockentity.TestTankBlockEntity;
import red.felnull.otyacraftengine.util.IKSGFluidUtil;

public class TestTankBlock extends IkisugiBaseEntityBlock {
    protected TestTankBlock(BlockBehaviour.Properties properties) {
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
            if (level.getBlockEntity(blockPos) instanceof TestTankBlockEntity) {
                if (!level.isClientSide()) {
                    TestTankBlockEntity ttbe = (TestTankBlockEntity) level.getBlockEntity(blockPos);
                    player.displayClientMessage(new TextComponent("Server"), false);
                    player.displayClientMessage(new TextComponent("Fluid: ").append(ttbe.getFluidTank(0).getFluidStack().getName()), false);
                    player.displayClientMessage(new TextComponent("Amont: " + ttbe.getFluidTank(0).getFluidStack().getAmount().intValue() + "mb").append("/").append(ttbe.getFluidTank(0).getCapacity() + "mb"), false);
                } else {
                    TestTankBlockEntity ttbe = (TestTankBlockEntity) level.getBlockEntity(blockPos);
                    player.displayClientMessage(new TextComponent("Client"), false);
                    player.displayClientMessage(new TextComponent("Fluid: ").append(ttbe.getFluidTank(0).getFluidStack().getName()), false);
                    player.displayClientMessage(new TextComponent("Amont: " + ttbe.getFluidTank(0).getFluidStack().getAmount().intValue() + "mb").append("/").append(ttbe.getFluidTank(0).getCapacity() + "mb"), false);
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

    @Override
    public BlockEntityType<?> getBlockEntityType() {
        return TestBlockEntity.TEST_TANK_BLOCKENTITY;
    }
}
