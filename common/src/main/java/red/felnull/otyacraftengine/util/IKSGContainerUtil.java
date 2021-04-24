package red.felnull.otyacraftengine.util;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import red.felnull.otyacraftengine.fluid.storage.FluidTank;

public class IKSGContainerUtil {
    public static CompoundTag saveAllTanks(CompoundTag compoundTag, NonNullList<FluidTank> nonNullList) {
        return saveAllTanks(compoundTag, nonNullList, true);
    }

    public static CompoundTag saveAllTanks(CompoundTag compoundTag, NonNullList<FluidTank> nonNullList, boolean bl) {
        ListTag listTag = new ListTag();

        for (int i = 0; i < nonNullList.size(); ++i) {
            FluidTank tank = nonNullList.get(i);
            if (!tank.isEmpty()) {
                CompoundTag compoundTag2 = new CompoundTag();
                compoundTag2.putByte("Number", (byte) i);
                tank.save(compoundTag2);
                listTag.add(compoundTag2);
            }
        }

        if (!listTag.isEmpty() || bl) {
            compoundTag.put("Tanks", listTag);
        }
        return compoundTag;
    }

    public static void loadAllTanks(CompoundTag compoundTag, NonNullList<FluidTank> nonNullList) {
        ListTag listTag = compoundTag.getList("Tanks", 10);

        for (int i = 0; i < listTag.size(); ++i) {
            CompoundTag compoundTag2 = listTag.getCompound(i);
            int j = compoundTag2.getByte("Number") & 255;
            if (j >= 0 && j < nonNullList.size()) {
                nonNullList.set(j, FluidTank.loadTank(compoundTag2, nonNullList.get(j).getCapacity(), nonNullList.get(j).getFilter()));
            }
        }

    }
}
