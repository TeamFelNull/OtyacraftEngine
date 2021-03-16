package red.felnull.otyacraftengine.util;

import net.minecraft.world.item.ItemStack;

public class IKSGItemUtil {
    public static ItemStack copyStackWithSize(ItemStack itemStack, int size) {
        if (size == 0)
            return ItemStack.EMPTY;
        ItemStack copy = itemStack.copy();
        copy.setCount(size);
        return copy;
    }
}
