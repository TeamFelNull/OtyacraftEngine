package red.felnull.otyacraftengine.asm.hook;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import red.felnull.otyacraftengine.api.event.client.RenderItemOverlayIntoGUIEvent;

public class RenderHook {
    public static void renderItemOverlayIntoGUIHook(FontRenderer fr, ItemStack stack, int x, int y, String text) {
        MinecraftForge.EVENT_BUS.post(new RenderItemOverlayIntoGUIEvent(fr, stack, x, y, text));
    }
}
