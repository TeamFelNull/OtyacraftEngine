package red.felnull.otyacraftengine.integration;

import red.felnull.otyacraftengine.api.IOEIntegration;
import red.felnull.otyacraftengine.api.OEIntegration;
import red.felnull.otyacraftengine.api.register.OEHandlerRegister;
import red.felnull.otyacraftengine.handler.TestHandler;

@OEIntegration
public class OtyacraftEngineIntegration implements IOEIntegration {
    @Override
    public void registrationHandler(OEHandlerRegister reg) {
        reg.register(TestHandler.class);
        // reg.register(TestEvent.class, TestHandler::onTest);
    }
}
