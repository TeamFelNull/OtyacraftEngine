package red.felnull.otyacraftengine.client;

import red.felnull.otyacraftengine.api.OtyacraftEngineAPI;
import red.felnull.otyacraftengine.api.register.OERegistries;
import red.felnull.otyacraftengine.client.handler.ClientHandler;
import red.felnull.otyacraftengine.client.keys.OEKeyMappings;
import red.felnull.otyacraftengine.client.renderer.blockentity.TestRenderer;

public class OtyacraftEngineClient {
    public static void clientInit() {
        OtyacraftEngineAPI api = OtyacraftEngineAPI.getInstance();
        api.isClient = true;
        ClientHandler.init();
        OERegistries.clientInit(api);
        OEKeyMappings.init(api);

        if (api.isTestMode()) {
            test();
        }
    }

    public static void test() {
        TestRenderer.init();
    }
}
