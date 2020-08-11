package red.felnull.otyacraftengine.client.handler;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IRegistryDelegate;
import red.felnull.otyacraftengine.client.util.IKSGClientUtil;
import red.felnull.otyacraftengine.item.IColorbleItem;

import java.util.Map;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ColorHandler {
    @SubscribeEvent
    public static void onItemColor(ColorHandlerEvent.Item e) {
        ItemColors c = e.getItemColors();
        Map<IRegistryDelegate<Item>, IItemColor> colors = IKSGClientUtil.getItemColorsColor(c);
        c.register(new IItemColor() {
            @Override
            public int getColor(ItemStack item, int layer) {

                return ((IColorbleItem) item.getItem()).getColoer(item, layer);
            }
        }, ForgeRegistries.ITEMS.getValues().stream().filter(n -> n instanceof IColorbleItem).filter(n -> !colors.containsKey(n.asItem().delegate)).toArray(IItemProvider[]::new));
    }
/*
    @SubscribeEvent
    public static void onBlockColor(ColorHandlerEvent.Block e) {
        BlockColors c = e.getBlockColors();
        ForgeRegistries.BLOCKS.getEntries().stream().filter(n -> n.getValue() instanceof IColorbleBlock).forEach(n -> {
            c.register(new IBlockColor() {
                @Override
                public int getColor(BlockState state, @Nullable IBlockDisplayReader iBlockDisplayReader, @Nullable BlockPos pos, int layer) {
                    return ((IColorbleBlock) n.getValue()).getColoer(state, iBlockDisplayReader, pos, layer);
                }
            });

        });
    }
*/
}

