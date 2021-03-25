package red.felnull.otyacraftengine.util;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import red.felnull.otyacraftengine.fluid.IkisugiFluidTank;

public class IKSGContainerUtil {
    public static CompoundTag saveAllTanks(CompoundTag compoundTag, NonNullList<IkisugiFluidTank> nonNullList) {
        return saveAllTanks(compoundTag, nonNullList, true);
    }

    public static CompoundTag saveAllTanks(CompoundTag compoundTag, NonNullList<IkisugiFluidTank> nonNullList, boolean bl) {
        ListTag listTag = new ListTag();

        for (int i = 0; i < nonNullList.size(); ++i) {
            IkisugiFluidTank tank = nonNullList.get(i);
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

    public static void loadAllTanks(CompoundTag compoundTag, NonNullList<IkisugiFluidTank> nonNullList) {
        ListTag listTag = compoundTag.getList("Tanks", 10);

        for (int i = 0; i < listTag.size(); ++i) {
            CompoundTag compoundTag2 = listTag.getCompound(i);
            int j = compoundTag2.getByte("Number") & 255;
            if (j >= 0 && j < nonNullList.size()) {
                IkisugiFluidTank tank = new IkisugiFluidTank();
                tank.load(compoundTag2);
                nonNullList.set(j, tank);
            }
        }

    }
}
