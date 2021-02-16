package red.felnull.otyacraftengine.api.event;

import red.felnull.otyacraftengine.api.OtyacraftEngineAPI;

public class OEEventBus {
    public static void callEvent(OEEvent event) {
        OtyacraftEngineAPI api = OtyacraftEngineAPI.getInstance();
        api.getHandlers().stream().filter(n -> n.canAccept(event)).forEach(n -> {
            n.accept(event);
        });
    }
}
