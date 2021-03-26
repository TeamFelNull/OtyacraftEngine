package red.felnull.otyacraftengine.item;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import red.felnull.otyacraftengine.fluid.IkisugiFluidTank;
import red.felnull.otyacraftengine.util.IKSGContainerUtil;

import java.util.Optional;

public interface IIkisugibleFluidTankItem {
    default Optional<IkisugiFluidTank> getPriorityFluidTank(ItemStack stack) {
        return getFluidTank(0, stack);
    }

    default int getPriorityCapacity(ItemStack stack) {
        return getCapacity(0, stack);
    }

    default Optional<ItemStack> setPriorityFluidTank(ItemStack stack, IkisugiFluidTank tank) {
        return setFluidTank(0, stack, tank);
    }

    ItemStack getEmptyFluidItem();

    default int getCapacity(int number, ItemStack stack) {
        return getFluidTank(number, stack).map(IkisugiFluidTank::getCapacity).orElse(getDefaultCapacity(number, stack));
    }

    int getDefaultCapacity(int number, ItemStack stack);

    default boolean canNotIncompleteFluidItem() {
        return false;
    }

    default Optional<ItemStack> setFluidTank(int number, ItemStack stack, IkisugiFluidTank tank) {
        NonNullList<IkisugiFluidTank> taks = getFluidTanks(stack).orElse(NonNullList.withSize(getFluidTankSize(stack), IkisugiFluidTank.createEmpty(getDefaultCapacity(number, stack))));
        taks.set(number, tank);
        return setFluidTanks(stack, taks);
    }

    default Optional<ItemStack> setFluidTanks(ItemStack stack, NonNullList<IkisugiFluidTank> tanks) {
        CompoundTag compoundTag = new CompoundTag();
        IKSGContainerUtil.saveAllTanks(compoundTag, tanks, false);
        if (!compoundTag.isEmpty()) {
            stack.addTagElement("BlockEntityTag", compoundTag);
        }
        return Optional.of(stack);
    }

    default Optional<IkisugiFluidTank> getFluidTank(int number, ItemStack stack) {
        return getFluidTanks(stack).map(n -> n.get(number));
    }

    default Optional<NonNullList<IkisugiFluidTank>> getFluidTanks(ItemStack stack) {
        NonNullList<IkisugiFluidTank> taks = NonNullList.withSize(getFluidTankSize(stack), IkisugiFluidTank.createEmpty());
        if (stack.hasTag() && stack.getTag().contains("BlockEntityTag")) {
            IKSGContainerUtil.loadAllTanks(stack.getTag().getCompound("BlockEntityTag"), taks);
        }

        for (int i = 0; i < taks.size(); i++) {
            if (taks.get(i).isEmpty()) {
                taks.set(i, IkisugiFluidTank.createEmpty(getDefaultCapacity(i, stack)));
            }
        }

        return Optional.of(taks);
    }

    default boolean isAllFluidEmpty(ItemStack stack) {
        return getFluidTanks(stack).map(n -> n.stream().allMatch(IkisugiFluidTank::isEmpty)).orElse(true);
    }

    int getFluidTankSize(ItemStack stack);
}
