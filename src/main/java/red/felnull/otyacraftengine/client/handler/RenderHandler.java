package red.felnull.otyacraftengine.client.handler;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import red.felnull.otyacraftengine.block.IColorbleBlock;
import red.felnull.otyacraftengine.item.IColorbleItem;

import javax.annotation.Nullable;


public class RenderHandler {

    @SubscribeEvent
    public static void onItemColor(ColorHandlerEvent.Item e) {
        ItemColors c = e.getItemColors();
        ForgeRegistries.ITEMS.getEntries().stream().filter(n -> n.getValue() instanceof IColorbleItem).forEach(n -> {
            c.register(new IItemColor() {
                @Override
                public int getColor(ItemStack item, int layer) {

                    return ((IColorbleItem) n.getValue()).getColoer(item, layer);
                }
            }, n.getValue());
        });
    }

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
}
