package red.felnull.otyacraftengine.item.storage;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import red.felnull.otyacraftengine.fluid.storage.FluidTank;

import java.util.Optional;

public interface INotIncompleteFluidTankItem extends IFluidTankItem {
    @Override
    default boolean canNotIncompleteFluidTankItem() {
        return true;
    }

    @Override
    default Optional<FluidTank> getFluidTank(ItemStack stack) {
        return getFluid(stack).map(n -> {
            FluidTank ift = FluidTank.createEmpty(getCapacity(stack));
            ift.setFluid(n);
            ift.setAmount(getCapacity(stack));
            return ift;
        });
    }

    Optional<Fluid> getFluid(ItemStack stack);

    @Override
    default boolean isFluidTankEmpty(ItemStack stack) {
        return getFluid(stack).isPresent();
    }
}
