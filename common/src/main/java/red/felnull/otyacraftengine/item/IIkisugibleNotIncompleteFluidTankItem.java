package red.felnull.otyacraftengine.item;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import red.felnull.otyacraftengine.fluid.IkisugiFluidTank;

import java.util.Optional;

public interface IIkisugibleNotIncompleteFluidTankItem extends IIkisugibleFluidTankItem {
    @Override
    default int getCapacity(int number, ItemStack stack) {
        return number == 0 ? getPriorityCapacity(stack) : 0;
    }

    @Override
    default Optional<ItemStack> setFluidTank(int number, ItemStack stack, IkisugiFluidTank tank) {
        return number == 0 ? setPriorityFluidTank(stack, tank) : Optional.empty();
    }

    @Override
    default Optional<ItemStack> setFluidTanks(ItemStack stack, NonNullList<IkisugiFluidTank> tanks) {
        return Optional.empty();
    }

    @Override
    default int getDefaultCapacity(int number, ItemStack stack) {
        return getPriorityCapacity(stack);
    }

    @Override
    default Optional<NonNullList<IkisugiFluidTank>> getFluidTanks(ItemStack stack) {
        if (getPriorityFluidTank(stack).isPresent()) {
            NonNullList<IkisugiFluidTank> list = NonNullList.create();
            list.set(0, getPriorityFluidTank(stack).get());
            return Optional.of(list);
        }
        return Optional.empty();
    }

    @Override
    default boolean canNotIncompleteFluidItem() {
        return true;
    }

    @Override
    default int getFluidTankSize(ItemStack stack) {
        return 1;
    }
}
