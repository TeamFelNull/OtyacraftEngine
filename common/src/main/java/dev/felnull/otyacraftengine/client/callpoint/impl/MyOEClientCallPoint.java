package dev.felnull.otyacraftengine.client.callpoint.impl;

import dev.felnull.otyacraftengine.client.callpoint.ClientCallPoint;
import dev.felnull.otyacraftengine.client.callpoint.ClientResourceListenerRegister;
import dev.felnull.otyacraftengine.client.shape.ClientIVShapeManager;

@ClientCallPoint.Sign
public class MyOEClientCallPoint implements ClientCallPoint {
    @Override
    public void onResourceListenerRegistry(ClientResourceListenerRegister register) {
        register.registerReloadListener(ClientIVShapeManager.getInstance());
    }
}
