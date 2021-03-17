package red.felnull.otyacraftengine.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import red.felnull.otyacraftengine.fluid.IkisugiFluidTank;

import java.util.Optional;

public interface IIkisugibleFluidTankItem {
    default Optional<IkisugiFluidTank> getFluidTank(ItemStack container) {
        if (container.hasTag() && container.getTag().contains("FluidTank")) {
            IkisugiFluidTank tank = new IkisugiFluidTank(getCapacity());
            tank.load(container.getTag().getCompound("FluidTank"));
            return Optional.of(tank);
        }
        return Optional.empty();
    }

    ItemStack getEmptyFluidItem();

    default Optional<ItemStack> setFluidTank(ItemStack stack, IkisugiFluidTank tank) {
        ItemStack out = stack.copy();
        out.getOrCreateTag().put("FluidTank", tank.save(new CompoundTag()));
        return Optional.of(out);
    }

    int getCapacity();

    default boolean canNotIncompleteFluidItem() {
        return false;
    }
}
