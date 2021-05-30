package red.felnull.otyacraftengine.client.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.entity.ItemRenderer;

public interface IIkisugibleWidget {
    default Minecraft getMinecraft() {
        return Minecraft.getInstance();
    }

    default Font getFont() {
        return getMinecraft().font;
    }

    default ItemRenderer getItemRenderer() {
        return getMinecraft().getItemRenderer();
    }
}
