package red.felnull.otyacraftengine;

import net.fabricmc.api.ModInitializer;
import red.felnull.otyacraftengine.fabric.init.RegistryInit;

public class OtyacraftEngineFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        OtyacraftEngine.init();
        RegistryInit.init();
    }
}
