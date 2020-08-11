package red.felnull.otyacraftengine.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import red.felnull.otyacraftengine.OtyacraftEngine;

//@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TestItem extends Item implements IDetailedInfomationItem, IColorbleItem {

    public TestItem(Properties properties) {
        super(properties);

    }

    @SubscribeEvent
    public static void onItemsRegistry(final RegistryEvent.Register<Item> e) {

        e.getRegistry().register(new TestItem(new Item.Properties().group(ItemGroup.MISC))
                .setRegistryName(OtyacraftEngine.MODID, "test"));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand hand) {
        ItemStack item = playerIn.getHeldItem(hand);

        if (worldIn.isRemote) {
            //    ServerDataSender.sending(PlayerHelper.getUUID(playerIn), UUID.randomUUID().toString(), new ResourceLocation(OtyacraftEngine.MODID, "test"), "ikisugi", FileLoadHelper.fileBytesReader(Paths.get("C:\\dev\\minecraft\\b\\ホラホラ.jpg")));
            //   DataSendReceiverManager.instance().sendToServer(new ResourceLocation(OtyacraftEngine.MODID, "textuerrequest"), "ikisugi", FileLoadHelper.fileBytesReader(Paths.get("C:\\dev\\minecraft\\b\\ホラホラ.jpg")));
            //  TextureUtil.getReceiveTexture("test");
            //    ReceiveTextureLoder.instance().updateTextuerClient(new ResourceLocation(OtyacraftEngine.MODID, "test"), "wa");
        }
        return ActionResult.func_233538_a_(item, worldIn.isRemote());
    }

    @Override
    public int getColoer(ItemStack item, int layer) {
        return 114514;
    }
}
