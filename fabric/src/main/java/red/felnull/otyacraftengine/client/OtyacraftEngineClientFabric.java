package red.felnull.otyacraftengine.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

public class OtyacraftEngineClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        OtyacraftEngineClient.clientInit();
    }
}
