package red.felnull.otyacraftengine.api;

import red.felnull.otyacraftengine.api.register.OEEventRegister;
import red.felnull.otyacraftengine.api.register.OEHandlerRegister;

/**
 * OEのイベント等の登録をするクラスにこのインターフェイスを実装する
 * Forgeは{@link OEIntegration}をつける、
 * Fabricはresources内の"fabric.mod.json"に"otyacraftengine"としてエントリーポイントを作成すれば読み込まれる
 *
 * @author MORIMORI0317
 * @since 2.0
 */
public interface IOEIntegration {
    /**
     * イベント登録
     *
     * @param evrg レジスター
     */
    default void registrationEvent(OEEventRegister evrg) {

    }

    default void registrationHandler(OEHandlerRegister hdrg) {

    }
}
