package red.felnull.otyacraftengine.client.gui.screen;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import red.felnull.otyacraftengine.inventory.IkisugiContainerMenu;

public interface IIkisugibleContainerScreen<T extends IkisugiContainerMenu> extends IIkisugibleScreen {

    default AbstractContainerScreen<T> getContainerScreen() {
        return (AbstractContainerScreen<T>) getScreen();
    }



}
