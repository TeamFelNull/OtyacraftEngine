package red.felnull.otyacraftengine.client.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;

public interface IIkisugibleWidget {
    default Minecraft getMinecraft() {
        return Minecraft.getInstance();
    }

    default Font getFont() {
        return getMinecraft().font;
    }
}
