package dev.felnull.otyacraftengine.client.callpoint;

import dev.felnull.otyacraftengine.resources.PlatformResourceReloadListener;

public interface ClientResourceListenerRegister {
    void registerReloadListener(PlatformResourceReloadListener<?> reloadListener);
}
