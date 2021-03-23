package red.felnull.otyacraftengine.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import red.felnull.otyacraftengine.OtyacraftEngine;

@Config(name = OtyacraftEngine.MODID)
public class OEConfig implements ConfigData {
    public boolean testMode = true;
}
