package red.felnull.otyacraftengine.util;

import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.NonNullList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IKSGNBTUtil {
    public static Map<String, String> readStringMap(CompoundNBT tag) {
        Map<String, String> map = new HashMap<>();
        tag.keySet().forEach(n -> map.put(n, tag.getString(n)));
        return map;
    }

    public static CompoundNBT writeStringMap(CompoundNBT tag, Map<String, String> map) {
        map.forEach((n, m) -> tag.putString(n, m));
        return tag;
    }

    public static Map<String, CompoundNBT> readNBTMap(CompoundNBT tag) {
        Map<String, CompoundNBT> map = new HashMap<>();
        tag.keySet().forEach(n -> map.put(n, tag.getCompound(n)));
        return map;
    }

    public static CompoundNBT writeNBTMap(CompoundNBT tag, Map<String, CompoundNBT> map) {
        map.forEach((n, m) -> tag.put(n, m));
        return tag;
    }

    public static void loadAllItemsByIKSG(CompoundNBT tag, NonNullList<ItemStack> list) {
        ListNBT listnbt = tag.getList("Items", 10);
        List<Integer> ints = new ArrayList<>();
        for (int i = 0; i < listnbt.size(); ++i) {
            CompoundNBT compoundnbt = listnbt.getCompound(i);
            int j = compoundnbt.getByte("Slot") & 255;
            if (j >= 0 && j < list.size()) {
                list.set(j, ItemStack.read(compoundnbt));
                ints.add(j);
            }
        }
        for (int i = 0; i < list.size(); i++) {
            if (!ints.contains(i)) {
                list.set(i, ItemStack.EMPTY);
            }
        }
    }

    public static CompoundNBT saveAllItemsByIKSG(CompoundNBT tag, NonNullList<ItemStack> list) {
        return ItemStackHelper.saveAllItems(tag, list);
    }
}
