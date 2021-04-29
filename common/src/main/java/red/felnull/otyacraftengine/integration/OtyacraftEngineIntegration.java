package red.felnull.otyacraftengine.integration;

import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.api.IOEIntegration;
import red.felnull.otyacraftengine.api.OEIntegration;
import red.felnull.otyacraftengine.api.OtyacraftEngineAPI;
import red.felnull.otyacraftengine.api.register.OEClientHandlerRegister;
import red.felnull.otyacraftengine.api.register.OEHandlerRegister;
import red.felnull.otyacraftengine.api.register.OEMODColorRegister;
import red.felnull.otyacraftengine.api.register.OEModelLoaderPathRegister;
import red.felnull.otyacraftengine.client.handler.ClientHandler;
import red.felnull.otyacraftengine.client.handler.TestClientHandler;
import red.felnull.otyacraftengine.handler.ServerHandler;
import red.felnull.otyacraftengine.handler.TestHandler;

@OEIntegration
public class OtyacraftEngineIntegration implements IOEIntegration {
    private static final OtyacraftEngineAPI api = OtyacraftEngineAPI.getInstance();

    @Override
    public void registrationHandler(OEHandlerRegister reg) {
        reg.register(ServerHandler.class);
        if (api.isTestMode()) {
            reg.register(TestHandler.class);
        }
    }

    @Override
    public void registrationClientHandler(OEClientHandlerRegister reg) {
        if (api.isTestMode()) {
            reg.register(TestClientHandler.class);
        }
        reg.register(ClientHandler.class);
    }

    @Override
    public void registrationMODColor(OEMODColorRegister reg) {
        reg.register("minecraft", 43520);
        reg.register("forge", 170);
        reg.register(OtyacraftEngine.MODID, 5635925);
    }

    @Override
    public void registrationModelLoaderPath(OEModelLoaderPathRegister reg) {
        reg.register("otyacraftengine");
    }
}
