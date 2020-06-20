package red.felnull.otyacraftengine.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.util.GorokuUtil;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TestItem extends Item implements IColorbleItem {

    public TestItem(Properties properties) {
        super(properties);

    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand hand) {
        ItemStack item = playerIn.getHeldItem(hand);
        playerIn.sendMessage(new StringTextComponent(GorokuUtil.getKurashikiMukaiyamaRoshutsuZuki()));
        return ActionResult.func_226248_a_(item);
    }

    @Override
    public int getColoer(ItemStack item, int layer) {

        return 1131796;
    }

    @SubscribeEvent
    public static void onItemsRegistry(final RegistryEvent.Register<Item> e) {

        e.getRegistry().register(new TestItem(new Item.Properties().group(ItemGroup.MISC))
                .setRegistryName(OtyacraftEngine.MODID, "test"));

    }

}
