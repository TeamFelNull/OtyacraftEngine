package red.felnull.otyacraftengine.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import red.felnull.otyacraftengine.api.register.OEHandlerRegister;
import red.felnull.otyacraftengine.api.register.OEMODColorRegister;

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
     * ハンドラー登録
     *
     * @param reg レジスター
     */
    default void registrationHandler(OEHandlerRegister reg) {

    }

    @Environment(EnvType.CLIENT)
    default void registrationClientHandler(OEHandlerRegister reg) {

    }

    default void registrationMODColor(OEMODColorRegister reg) {

    }
}
