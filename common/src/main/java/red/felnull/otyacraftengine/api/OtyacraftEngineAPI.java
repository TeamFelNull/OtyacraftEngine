package red.felnull.otyacraftengine.api;

import red.felnull.otyacraftengine.throwable.EarlyAccessError;

import java.util.List;
import java.util.function.Consumer;

/**
 * OtyacraftEngineのAPI
 *
 * @author MORIMORI0317
 * @since 2.0
 */
public class OtyacraftEngineAPI {
    private static OtyacraftEngineAPI INSTANCE;
    private final List<IOEIntegration> integrations;

    public OtyacraftEngineAPI(List<IOEIntegration> integrations) {
        this.integrations = integrations;
        INSTANCE = this;
    }

    /**
     * APIのインスタンスを取得
     *
     * @return OEAPIのインスタンス
     */
    public static OtyacraftEngineAPI getInstance() {
        if (INSTANCE == null) {
            throw new EarlyAccessError();
        }
        return INSTANCE;
    }

    public List<IOEIntegration> getIntegrations() {
        return integrations;
    }

    public void integrations(Consumer<IOEIntegration> consumer) {
        List<IOEIntegration> integrations = getIntegrations();
        integrations.forEach(consumer::accept);
    }
}
