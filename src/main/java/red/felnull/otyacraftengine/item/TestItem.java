package red.felnull.otyacraftengine.item;

import net.minecraft.block.AnvilBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import red.felnull.otyacraftengine.OtyacraftEngine;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TestItem extends Item implements IDetailedInfomationItem {

    public TestItem(Properties properties) {
        super(properties);

    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand hand) {
        ItemStack item = playerIn.getHeldItem(hand);

        if (!worldIn.isRemote) {
        //    playerIn.sendStatusMessage(new StringTextComponent(X_AXIS_AABB.toString()), false);

        }

        return ActionResult.func_233538_a_(item, worldIn.isRemote());
    }


    @SubscribeEvent
    public static void onItemsRegistry(final RegistryEvent.Register<Item> e) {

        e.getRegistry().register(new TestItem(new Item.Properties().group(ItemGroup.MISC))
                .setRegistryName(OtyacraftEngine.MODID, "test"));

    }

}
