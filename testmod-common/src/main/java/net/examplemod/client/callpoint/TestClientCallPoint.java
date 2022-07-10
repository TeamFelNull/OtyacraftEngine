package net.examplemod.client.callpoint;

import dev.felnull.otyacraftengine.client.callpoint.ClientCallPoint;
import dev.felnull.otyacraftengine.client.callpoint.ModelRegister;
import net.examplemod.client.model.TestModels;

@ClientCallPoint.Sign
public class TestClientCallPoint implements ClientCallPoint {
    @Override
    public void onModelRegistry(ModelRegister register) {
        TestModels.init(register);
    }
}
