package red.felnull.otyacraftengine.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import red.felnull.otyacraftengine.fluid.IkisugiFluidTank;
import red.felnull.otyacraftengine.util.IKSGItemUtil;

import java.util.Optional;

public interface IIkisugibleFluidTankItem {
    default Optional<IkisugiFluidTank> getFluidTank(ItemStack stack) {
        if (stack.hasTag() && stack.getTag().contains("FluidTank")) {
            IkisugiFluidTank tank = new IkisugiFluidTank(getCapacity(stack));
            tank.load(stack.getTag().getCompound("FluidTank"));
            return Optional.of(tank);
        }
        return Optional.of(new IkisugiFluidTank(getCapacity(stack)));
    }

    ItemStack getEmptyFluidItem();

    default Optional<ItemStack> setFluidTank(ItemStack stack, IkisugiFluidTank tank) {
        stack.getOrCreateTag().put("FluidTank", tank.save(new CompoundTag()));
        return Optional.of(stack);
    }

    int getCapacity(ItemStack stack);

    default boolean canNotIncompleteFluidItem() {
        return false;
    }
}
