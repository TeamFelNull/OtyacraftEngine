package dev.felnull.otyacraftengine;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = OtyacraftEngine.MODID)
@Config.Gui.Background(OtyacraftEngine.MODID + ":textures/gui/config_background.png")
public class OEConfig implements ConfigData {
    @ConfigEntry.Gui.RequiresRestart
    public boolean testMode = false;
}
