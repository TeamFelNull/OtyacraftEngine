package dev.felnull.otyacraftengine.client.callpoint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface ClientCallPoint {
    default void onModelRegistry(ModelRegister register) {
    }

    default void onLayerRegistry(LayerRegister register) {
    }

    default void onResourceListenerRegistry(ClientResourceListenerRegister register) {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @interface Sign {
    }
}
