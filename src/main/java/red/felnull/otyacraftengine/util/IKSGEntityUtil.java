package red.felnull.otyacraftengine.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

public class IKSGEntityUtil {
    public static ItemEntity createItemEntity(ItemStack item, World worldIn, double x, double y, double z) {
        ItemEntity iteme = new ItemEntity(worldIn, x, y, z);
        iteme.setItem(item);
        iteme.setDefaultPickupDelay();
        return iteme;
    }

    public static ItemEntity createItemEntity(ItemStack item, World worldIn) {
        return createItemEntity(item, worldIn, 0, 0, 0);
    }

    public static void addMobDrop(LivingDropsEvent e, ItemStack item) {
        Entity en = e.getEntity();
        e.getDrops().add(createItemEntity(item, en.world, en.getPosX(), en.getPosY(), en.getPosZ()));
    }

}
