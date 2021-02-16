package red.felnull.otyacraftengine.integration;

import red.felnull.otyacraftengine.api.IOEIntegration;
import red.felnull.otyacraftengine.api.OEIntegration;
import red.felnull.otyacraftengine.api.event.TestEvent;
import red.felnull.otyacraftengine.api.register.OEHandlerRegister;
import red.felnull.otyacraftengine.handler.TestHandler;

@OEIntegration
public class OtyacraftEngineIntegration implements IOEIntegration {
    @Override
    public void registrationHandler(OEHandlerRegister hdrg) {
        hdrg.register(TestEvent.class, TestHandler::onTest);
    }
}
