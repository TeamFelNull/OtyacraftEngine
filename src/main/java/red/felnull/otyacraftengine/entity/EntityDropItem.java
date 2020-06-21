package red.felnull.otyacraftengine.entity;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import red.felnull.otyacraftengine.registries.OERegistries;
import red.felnull.otyacraftengine.util.EntityUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EntityDropItem {


    public void addDrop(LivingDropsEvent e) {

        List<ItemStack> dropedItem = new ArrayList<ItemStack>();

        for (Map.Entry<ResourceLocation, EntityAddDropItem> drops : OERegistries.ENTITY_DROP.entrySet()) {

            if (drops.getValue().isDoubleDrop()) {
                drops.getValue().getDropList(e).forEach(i -> dropedItem.add(i));
            } else {

                for (ItemStack st : drops.getValue().getDropList(e)) {
                    boolean dflag = true;
                    for (ItemStack sta : dropedItem) {
                        if (sta.getItem() == st.getItem()) {
                            dflag = false;
                        }
                    }
                    if (dflag) {
                        dropedItem.add(st);
                    }
                }
            }
        }


        dropedItem.forEach(i -> drop(e, i));

    }

    private void drop(LivingDropsEvent e, ItemStack stack) {
        World worldIn = e.getEntityLiving().getEntityWorld();
        double x = e.getEntityLiving().func_226277_ct_();
        double y = e.getEntityLiving().func_226278_cu_();
        double z = e.getEntityLiving().func_226281_cx_();
        e.getDrops().add(EntityUtil.createItemEntity(stack, worldIn, x, y, z));
    }
}
