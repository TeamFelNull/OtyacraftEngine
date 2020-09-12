package red.felnull.otyacraftengine.client.gui.screen;

import red.felnull.otyacraftengine.OtyacraftEngine;

public interface IOpendScreen {

    default boolean isOpend() {
        return OtyacraftEngine.proxy.getMinecraft().currentScreen == this;
    }

}
