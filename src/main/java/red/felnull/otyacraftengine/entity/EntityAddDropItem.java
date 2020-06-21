package red.felnull.otyacraftengine.entity;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import red.felnull.otyacraftengine.util.GorokuUtil;

import java.util.ArrayList;
import java.util.List;

public class EntityAddDropItem {

    public boolean isDoubleDrop() {
        return true;
    }

    public List<ItemStack> getDropList(LivingDropsEvent e) {
        List<ItemStack> list = new ArrayList<ItemStack>();
        list.add(new ItemStack(Items.APPLE).setDisplayName(new StringTextComponent(GorokuUtil.getSenzuri())));
        return list;
    }
}
