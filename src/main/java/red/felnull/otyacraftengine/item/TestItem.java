package red.felnull.otyacraftengine.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potions;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

//@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TestItem extends Item {

    //  public static final Item TEST = new TestItem(new Item.Properties().group(ItemGroup.MISC)).setRegistryName(OtyacraftEngine.MODID, "test");

    public TestItem(Properties properties) {
        super(properties.maxStackSize(1).maxDamage(10));
    }

    //  @SubscribeEvent
    //   public static void onItemsRegistry(final RegistryEvent.Register<Item> e) {
    //       e.getRegistry().register(TEST);
    //   }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (!worldIn.isRemote) {
            String str = Potions.POISON.getTags().toString();
            playerIn.sendStatusMessage(new StringTextComponent(str), false);
        }
        return ActionResult.func_233538_a_(itemstack, worldIn.isRemote());
    }

}
