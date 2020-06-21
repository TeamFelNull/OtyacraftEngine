package red.felnull.otyacraftengine.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import red.felnull.otyacraftengine.util.GorokuUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimpleEntityAddDropItem extends EntityAddDropItem {
    private EntityType entityType;
    private ItemStack stack;
    private int chanse;

    public SimpleEntityAddDropItem(EntityType entityType, ItemStack stack, int chanse) {
        this.entityType = entityType;
        this.stack = stack;
        this.chanse = chanse;
    }
    @Override
    public boolean isDoubleDrop() {
        return false;
    }
    @Override
    public List<ItemStack> getDropList(LivingDropsEvent e) {
        List<ItemStack> list = new ArrayList<ItemStack>();

        if (e.getEntityLiving().getType() == entityType) {
            Random r = new Random();
            if (r.nextInt(100) <= chanse) {
                list.add(stack);
            }
        }

        return list;
    }
}
