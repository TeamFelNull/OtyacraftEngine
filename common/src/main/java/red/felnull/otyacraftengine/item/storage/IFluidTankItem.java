package red.felnull.otyacraftengine.item.storage;

import me.shedaniel.architectury.fluid.FluidStack;
import net.minecraft.world.item.ItemStack;
import red.felnull.otyacraftengine.fluid.storage.FluidTank;

import java.util.Optional;

public interface IFluidTankItem {
    Optional<FluidTank> getFluidTank(ItemStack stack);

    ItemStack getEmptyFluidTankItem();

    boolean isFluidTankEmpty(ItemStack stack);

    Optional<ItemStack> setFluidTank(ItemStack stack, FluidTank tank);

    int getCapacity(ItemStack stack);

    default boolean canNotIncompleteFluidTankItem() {
        return false;
    }

    default boolean isFluidTankItem(ItemStack stack) {
        return true;
    }

    default boolean fluidFilter(FluidStack stack) {
        return true;
    }

}
