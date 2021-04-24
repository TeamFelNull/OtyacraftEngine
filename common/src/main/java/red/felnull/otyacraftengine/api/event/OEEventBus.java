package red.felnull.otyacraftengine.api.event;

import red.felnull.otyacraftengine.api.OtyacraftEngineAPI;

/**
 * OEEvent関係
 *
 * @author MORIMORI0317
 * @since 2.0
 */
public class OEEventBus {
    /**
     * OEのイベントを呼び出したいときに使用してください
     *
     * @param event 呼び出したいイベント
     * @return キャンセルされてるか
     */
    public static boolean post(OEEvent event) {
        OtyacraftEngineAPI api = OtyacraftEngineAPI.getInstance();
        api.getHandlers().stream().filter(n -> n.canAccept(event)).forEach(n -> {
            n.accept(event);
        });

        if (api.isClient()) {
            api.getClientHandlers().stream().filter(n -> n.canAccept(event)).forEach(n -> {
                n.accept(event);
            });
        }

        return event.isCanceled();
    }
}
