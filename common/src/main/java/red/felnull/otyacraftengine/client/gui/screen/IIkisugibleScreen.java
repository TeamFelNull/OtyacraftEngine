package red.felnull.otyacraftengine.client.gui.screen;

import net.minecraft.client.gui.screens.Screen;
import red.felnull.otyacraftengine.client.gui.components.IIkisugibleWidget;

public interface IIkisugibleScreen extends IIkisugibleWidget {

    Screen getScreen();

    default boolean isOpened() {
        return getMinecraft().screen == getScreen();
    }
}
