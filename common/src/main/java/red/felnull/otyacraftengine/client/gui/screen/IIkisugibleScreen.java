package red.felnull.otyacraftengine.client.gui.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

public interface IIkisugibleScreen {
    default Minecraft getMinecraft() {
        return Minecraft.getInstance();
    }

    Screen getScreen();
}
