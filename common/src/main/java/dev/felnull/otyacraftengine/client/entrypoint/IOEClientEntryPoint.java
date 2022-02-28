package dev.felnull.otyacraftengine.client.entrypoint;

import dev.felnull.otyacraftengine.client.model.ModelRegister;

public interface IOEClientEntryPoint {
    default void onModelRegistry(ModelRegister register) {
    }
}
