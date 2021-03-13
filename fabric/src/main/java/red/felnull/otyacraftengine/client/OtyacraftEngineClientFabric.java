package red.felnull.otyacraftengine.client;

import net.fabricmc.api.ClientModInitializer;
import red.felnull.otyacraftengine.api.OtyacraftEngineAPI;
import red.felnull.otyacraftengine.client.client.fabric.RenderHandler;

public class OtyacraftEngineClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        OtyacraftEngineClient.clientInit();
        RenderHandler.init();

        OtyacraftEngineAPI api = OtyacraftEngineAPI.getInstance();
        if (api.isTestMode()) {
           // TestFabricEvent.init();
        }
    }
}
