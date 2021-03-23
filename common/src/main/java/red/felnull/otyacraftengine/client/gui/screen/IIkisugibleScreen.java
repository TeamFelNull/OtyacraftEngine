package red.felnull.otyacraftengine.client.gui.screen;

import net.minecraft.client.Minecraft;

public interface IIkisugibleScreen {
    default Minecraft getMinecraft() {
        return Minecraft.getInstance();
    }
}
