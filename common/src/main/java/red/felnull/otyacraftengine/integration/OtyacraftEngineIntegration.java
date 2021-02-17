package red.felnull.otyacraftengine.integration;

import red.felnull.otyacraftengine.api.IOEIntegration;
import red.felnull.otyacraftengine.api.OEIntegration;
import red.felnull.otyacraftengine.api.OtyacraftEngineAPI;
import red.felnull.otyacraftengine.api.register.OEHandlerRegister;
import red.felnull.otyacraftengine.client.handler.ClientHandler;
import red.felnull.otyacraftengine.client.handler.TestClientHandler;
import red.felnull.otyacraftengine.handler.TestHandler;

@OEIntegration
public class OtyacraftEngineIntegration implements IOEIntegration {
    @Override
    public void registrationHandler(OEHandlerRegister reg) {
        OtyacraftEngineAPI api = OtyacraftEngineAPI.getInstance();
        if (api.isTestMode()) {
            reg.register(TestHandler.class);
        }
    }

    @Override
    public void registrationClientHandler(OEHandlerRegister reg) {
        OtyacraftEngineAPI api = OtyacraftEngineAPI.getInstance();
        if (api.isTestMode()) {
            reg.register(TestClientHandler.class);
        }
        reg.register(ClientHandler.class);
    }
}
