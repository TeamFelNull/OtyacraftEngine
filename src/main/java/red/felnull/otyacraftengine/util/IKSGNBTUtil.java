package red.felnull.otyacraftengine.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
    private static final Gson gson = new GsonBuilder().create();

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

    public static CompoundNBT writeStringList(CompoundNBT tag, List<String> strs) {
        for (int i = 0; i < strs.size(); i++) {
            tag.putString(String.valueOf(i), strs.get(i));
        }
        return tag;
    }

    public static List<String> readStringList(CompoundNBT tag) {
        List<String> list = new ArrayList();
        for (int i = 0; i < tag.keySet().size(); i++) {
            list.add(tag.getString(String.valueOf(i)));
        }
        return list;
    }

    public static CompoundNBT addStringList(CompoundNBT tag, String str) {
        tag.putString(String.valueOf(tag.keySet().size()), str);
        return tag;
    }

}
