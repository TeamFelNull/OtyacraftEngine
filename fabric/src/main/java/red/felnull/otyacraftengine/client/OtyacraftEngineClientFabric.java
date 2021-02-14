package red.felnull.otyacraftengine.client;

import net.fabricmc.api.ClientModInitializer;

public class OtyacraftEngineClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        OtyacraftEngineClient.clientInit();
    }
}
