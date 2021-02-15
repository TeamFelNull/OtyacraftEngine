package red.felnull.otyacraftengine.integration;

import red.felnull.otyacraftengine.api.IOEIntegration;
import red.felnull.otyacraftengine.api.OEIntegration;
import red.felnull.otyacraftengine.api.register.OEEventRegister;

@OEIntegration
public class OtyacraftEngineIntegration implements IOEIntegration {
    @Override
    public void registrationEvent(OEEventRegister rg) {
        rg.register(null);
    }
}
