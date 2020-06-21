package red.felnull.otyacraftengine.registries;

import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.entity.EntityAddDropItem;
import red.felnull.otyacraftengine.entity.SimpleEntityAddDropItem;

import java.util.HashMap;
import java.util.Map;

public class OERegistries {
    public static Map<String, Integer> MOD_COLOR = new HashMap<String, Integer>();
    public static Map<ResourceLocation, EntityAddDropItem> ENTITY_DROP = new HashMap<ResourceLocation, EntityAddDropItem>();

    public static void init() {
        registrierModColor("minecraft", 43520);
        registrierModColor("forge", 170);
        registrierModColor(OtyacraftEngine.MODID, 5635925);

        //  registrierEntityDrop(new ResourceLocation(OtyacraftEngine.MODID, "test"), new SimpleEntityAddDropItem(EntityType.CREEPER, new ItemStack(Items.BOW), 50));
        //  registrierEntityDrop(new ResourceLocation(OtyacraftEngine.MODID, "test2"), new SimpleEntityAddDropItem(EntityType.COW, new ItemStack(Items.STICK), 50));

    }

    public static void registrierModColor(String modid, int color) {
        OERegistries.MOD_COLOR.put(modid, color);
    }

    public static void registrierEntityDrop(ResourceLocation location, EntityAddDropItem drop) {
        OERegistries.ENTITY_DROP.put(location, drop);
    }


}
