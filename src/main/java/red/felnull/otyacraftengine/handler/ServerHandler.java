package red.felnull.otyacraftengine.handler;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import red.felnull.otyacraftengine.entity.EntityDropItem;
import red.felnull.otyacraftengine.util.EntityUtil;

public class ServerHandler {
    @SubscribeEvent
    public static void onMobDrop(LivingDropsEvent e) {


        EntityDropItem edi = new EntityDropItem();
        edi.addDrop(e);


    }
}
