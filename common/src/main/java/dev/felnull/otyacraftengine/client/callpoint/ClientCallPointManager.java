package dev.felnull.otyacraftengine.client.callpoint;

import dev.felnull.otyacraftengine.client.callpoint.impl.ClientCallPointManagerImpl;

public interface ClientCallPointManager {
    static ClientCallPointManager getInstance() {
        return ClientCallPointManagerImpl.INSTANCE;
    }

    ClientCallPoint call();
}
