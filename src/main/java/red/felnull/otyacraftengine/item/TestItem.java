package red.felnull.otyacraftengine.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.data.WorldDataManager;

//@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TestItem extends Item implements IDetailedInfomationItem {

    public TestItem(Properties properties) {
        super(properties);

    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand hand) {
        ItemStack item = playerIn.getHeldItem(hand);

        //   if (worldIn.isRemote) {

        //  if (playerIn.isCrouching()) {
        //   WorldDataManager.instance().getPlayerData(playerIn, new ResourceLocation(OtyacraftEngine.MODID, "test")).putInt("test", 510);
        //   } else {
        playerIn.sendStatusMessage(new StringTextComponent(WorldDataManager.instance().getPlayerData(playerIn, new ResourceLocation(OtyacraftEngine.MODID, "test"), worldIn.isRemote).toString()), false);
        //   }
        //   }

        return ActionResult.func_233538_a_(item, worldIn.isRemote());
    }

/*
    @SubscribeEvent
    public static void onItemsRegistry(final RegistryEvent.Register<Item> e) {

        e.getRegistry().register(new TestItem(new Item.Properties().group(ItemGroup.MISC))
                .setRegistryName(OtyacraftEngine.MODID, "test"));

    }
*/
}
