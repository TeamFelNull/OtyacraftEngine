package red.felnull.otyacraftengine.api;

import red.felnull.otyacraftengine.api.event.OEEvent;


public interface IHandler {
    void accept(OEEvent ev);

    boolean canAccept(OEEvent ev);
}
