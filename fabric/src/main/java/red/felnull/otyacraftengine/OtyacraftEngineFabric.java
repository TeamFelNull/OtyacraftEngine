package red.felnull.otyacraftengine;

import net.fabricmc.api.ModInitializer;
import red.felnull.otyacraftengine.init.fabric.RegistryInit;

public class OtyacraftEngineFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        OtyacraftEngine.init();
        RegistryInit.init();
    }
}
