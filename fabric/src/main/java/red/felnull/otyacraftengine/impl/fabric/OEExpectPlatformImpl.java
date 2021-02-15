package red.felnull.otyacraftengine.impl.fabric;

import net.fabricmc.loader.api.FabricLoader;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.api.IOEIntegration;

import java.util.List;

public class OEExpectPlatformImpl {
    public static List<IOEIntegration> getIntegrations() {
        return FabricLoader.getInstance().getEntrypoints(OtyacraftEngine.MODID, IOEIntegration.class);
    }
}
