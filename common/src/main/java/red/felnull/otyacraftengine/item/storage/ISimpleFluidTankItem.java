package red.felnull.otyacraftengine.item.storage;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import red.felnull.otyacraftengine.fluid.storage.FluidTank;

import java.util.Optional;

public interface ISimpleFluidTankItem extends IFluidTankItem {
    @Override
    default Optional<FluidTank> getFluidTank(ItemStack stack) {
        if (stack.hasTag() && stack.getTag().contains("FluidTank")) {
            return Optional.of(FluidTank.loadTank(stack.getTag().getCompound("FluidTank"), getCapacity(stack), n -> fluidFilter(stack, n)));
        }
        return Optional.of(FluidTank.createEmpty(getCapacity(stack), n -> fluidFilter(stack, n)));
    }

    @Override
    default Optional<ItemStack> setFluidTank(ItemStack stack, FluidTank tank) {
        stack.getOrCreateTag().put("FluidTank", tank.save(new CompoundTag()));
        return Optional.of(stack);
    }

    @Override
    default boolean isFluidTankEmpty(ItemStack stack) {
        return getFluidTank(stack).map(FluidTank::isEmpty).orElse(false);
    }
}
