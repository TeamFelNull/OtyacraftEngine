package red.felnull.otyacraftengine.client.handler;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import red.felnull.otyacraftengine.item.IColorbleItem;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
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
}
