package red.felnull.otyacraftengine.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
    private final boolean isTestMode;
    private final boolean isDebugMode;
    public boolean isClient;

    public OtyacraftEngineAPI(List<IOEIntegration> integrations, boolean testmode, boolean debugmode) {
        this.integrations = integrations;
        this.isTestMode = testmode;
        this.isDebugMode = debugmode;

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

    @Environment(EnvType.CLIENT)
    public Set<IHandler> getClientHandlers() {
        OEHandlerRegister handlers = (OEHandlerRegister) OERegistries.getSingleRegistry(new ResourceLocation(OtyacraftEngine.MODID, "client_handler"));
        return handlers.getSet();
    }


    public boolean isDebugMode() {
        return isDebugMode;
    }

    public boolean isTestMode() {
        return isTestMode;
    }

    public boolean isClient() {
        return isClient;
    }
}
