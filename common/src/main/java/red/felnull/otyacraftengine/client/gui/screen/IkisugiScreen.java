package red.felnull.otyacraftengine.client.gui.screen;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class IkisugiScreen extends Screen implements IIkisugibleScreen {
    protected IkisugiScreen(Component component) {
        super(component);
    }

    @Override
    public Screen getScreen() {
        return this;
    }
}
