package dev.felnull.otyacraftenginetest.client.callpoint;

import dev.felnull.otyacraftengine.client.callpoint.ClientCallPoint;
import dev.felnull.otyacraftengine.client.callpoint.ModelRegister;
import dev.felnull.otyacraftenginetest.client.model.TestModels;

@ClientCallPoint.Sign
public class TestOECallPoint implements ClientCallPoint {
    @Override
    public void onModelRegistry(ModelRegister register) {
        TestModels.init(register);
    }
}
