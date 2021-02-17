package red.felnull.otyacraftengine.api;

import net.minecraft.resources.ResourceLocation;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.api.register.OEHandlerRegister;
import red.felnull.otyacraftengine.api.register.OERegistries;
import red.felnull.otyacraftengine.throwable.EarlyAccessError;

import java.util.List;
import java.util.Set;
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

    /**
     * OEを利用しているMODの登録情報
     *
     * @return 取得
     */
    public List<IOEIntegration> getIntegrations() {
        return integrations;
    }

    public void integrationConsumer(Consumer<IOEIntegration> consumer) {
        List<IOEIntegration> integrations = getIntegrations();
        integrations.forEach(consumer::accept);
    }

    public Set<IHandler> getHandlers() {
        OEHandlerRegister handlers = (OEHandlerRegister) OERegistries.getSingleRegistry(new ResourceLocation(OtyacraftEngine.MODID, "handler"));
        return handlers.getSet();
    }
}
