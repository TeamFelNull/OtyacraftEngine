package dev.felnull.otyacraftengine;

import dev.felnull.otyacraftengine.client.debug.HighlightVoxelShapeType;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = OtyacraftEngine.MODID)
@Config.Gui.Background(OtyacraftEngine.MODID + ":textures/gui/config_background.png")
public class OEConfig implements ConfigData {

    public boolean ikisugiVoxelShape = true;

    //public int maxURLTextureLoadThreadCount = 5;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("debug")
    public boolean testMode = false;

    @ConfigEntry.Category("debug")
    public HighlightVoxelShapeType highlightVoxelShape = HighlightVoxelShapeType.OFF;

    @ConfigEntry.Category("debug")
    public boolean showTagTooltip = false;

    @ConfigEntry.Category("debug")
    public boolean showModNameTooltip = false;

    @ConfigEntry.Category("debug")
    public boolean showWidgetData = false;
}
