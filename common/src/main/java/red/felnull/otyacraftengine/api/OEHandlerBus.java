package red.felnull.otyacraftengine.api;

import red.felnull.otyacraftengine.api.event.OEEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * OEのハンドラー関係
 *
 * @author MORIMORI0317
 * @since 2.0
 */
public class OEHandlerBus {
    /**
     * OEのイベントを呼び出したいときに使用してください
     *
     * @param event 呼び出したいイベント
     * @return キャンセルされてるか
     */
    public static boolean post(OEEvent event) {
        postHandler(event);
        return event.isCanceled();
    }

    /**
     * OEのハンドラーを呼び出したいときに使用してください
     *
     * @param object 呼び出したいオブジェクト
     */
    public static void postHandler(Object object) {
        OtyacraftEngineAPI api = OtyacraftEngineAPI.getInstance();
        api.getHandlers().stream().filter(n -> n.canAccept(object, null)).forEach(n -> n.accept(object));
        if (api.isClient()) {
            api.getClientHandlers().stream().filter(n -> n.canAccept(object, null)).forEach(n -> n.accept(object));
        }
    }

    /**
     * OEのハンドラー(返し値あり)を呼び出したいときに使用してください
     *
     * @param object   object 呼び出したいオブジェクト
     * @param returner 返される値のクラス
     * @return 返された値リスト
     */
    public static <R> List<R> postReturnHandler(Object object, Class<R> returner) {
        OtyacraftEngineAPI api = OtyacraftEngineAPI.getInstance();
        List<R> rs = new ArrayList<>(api.getHandlers().stream().filter(n -> n.canAccept(object, returner)).map(n -> returner.cast(n.accept(object))).toList());
        if (api.isClient()) {
            rs.addAll(api.getClientHandlers().stream().filter(n -> n.canAccept(object, returner)).map(n -> returner.cast(n.accept(object))).toList());
        }
        return rs;
    }
}
