package red.felnull.otyacraftengine.integration;

import me.shedaniel.architectury.platform.Platform;
import red.felnull.otyacraftengine.api.IOEIntegration;
import red.felnull.otyacraftengine.api.OEIntegration;
import red.felnull.otyacraftengine.api.OtyacraftEngineAPI;
import red.felnull.otyacraftengine.api.register.OEHandlerRegister;
import red.felnull.otyacraftengine.api.register.OEMODColorRegister;
import red.felnull.otyacraftengine.client.handler.ClientHandler;
import red.felnull.otyacraftengine.client.handler.TestClientHandler;
import red.felnull.otyacraftengine.handler.TestHandler;

import java.util.Random;

@OEIntegration
public class OtyacraftEngineIntegration implements IOEIntegration {
    private static final OtyacraftEngineAPI api = OtyacraftEngineAPI.getInstance();

    @Override
    public void registrationHandler(OEHandlerRegister reg) {
        if (api.isTestMode()) {
            reg.register(TestHandler.class);
        }
    }

    @Override
    public void registrationClientHandler(OEHandlerRegister reg) {
        if (api.isTestMode()) {
            reg.register(TestClientHandler.class);
        }
        reg.register(ClientHandler.class);
    }

    @Override
    public void registrationMODColor(OEMODColorRegister reg) {
        reg.register("minecraft", 43520);
        reg.register("forge", 170);
        // reg.register(OtyacraftEngine.MODID, 5635925);
    }
}
