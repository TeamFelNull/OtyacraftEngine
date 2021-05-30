package red.felnull.otyacraftengine.api;

import dev.architectury.platform.Platform;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.InteractionResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.api.register.OEClientHandlerRegister;
import red.felnull.otyacraftengine.api.register.OEHandlerRegister;
import red.felnull.otyacraftengine.api.register.OEMODColorRegister;
import red.felnull.otyacraftengine.api.register.OERegistries;
import red.felnull.otyacraftengine.config.OEConfig;
import red.felnull.otyacraftengine.util.IKSGModUtil;

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
    private static final Logger LOGGER = LogManager.getLogger(OtyacraftEngineAPI.class);
    private static OtyacraftEngineAPI INSTANCE;
    private final List<IOEIntegration> integrations;
    private final boolean isTestMode;
    private boolean isDebugMode;

    private OtyacraftEngineAPI(List<IOEIntegration> integrations, boolean testmode) {
        this.integrations = integrations;
        this.isTestMode = testmode;
    }

    /**
     * APIのインスタンスを取得
     *
     * @return OEAPIのインスタンス
     */
    public static OtyacraftEngineAPI getInstance() {
        if (INSTANCE == null) {
            apiInit();
        }
        return INSTANCE;
    }

    private static void apiInit() {
        boolean testmode = OtyacraftEngine.CONFIG.testMode;
        OtyacraftEngineAPI api = new OtyacraftEngineAPI(IKSGModUtil.getModEntrypoints(IOEIntegration.class, OtyacraftEngine.MODID, OEIntegration.class), testmode);
        api.setDebugMode(OtyacraftEngine.CONFIG.debugMode);
        if (INSTANCE == null) {
            LOGGER.info("API Initialize");
            long startTime = System.currentTimeMillis();
            INSTANCE = api;
            AutoConfig.getConfigHolder(OEConfig.class).registerSaveListener((manager, data) -> {
                INSTANCE.setDebugMode(data.debugMode);
                return InteractionResult.SUCCESS;
            });
            LOGGER.info("API Initialize elapsed time: " + (System.currentTimeMillis() - startTime) + "ms");
        }
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
        integrations.forEach(consumer);
    }

    public Set<IHandler> getHandlers() {
        OEHandlerRegister handlers = OERegistries.getRegistry(OEHandlerRegister.class);
        return handlers.getSet();
    }

    @Environment(EnvType.CLIENT)
    public Set<IHandler> getClientHandlers() {
        OEClientHandlerRegister handlers = OERegistries.getRegistry(OEClientHandlerRegister.class);
        return handlers.getSet();
    }


    public boolean isDebugMode() {
        return isDebugMode;
    }

    public void setDebugMode(boolean debugMode) {
        isDebugMode = debugMode;
    }

    public boolean isTestMode() {
        return isTestMode;
    }

    public boolean isClient() {
        return Platform.getEnv() == EnvType.CLIENT;
    }

    public int getModColor(String modid) {
        OEMODColorRegister colors = OERegistries.getRegistry(OEMODColorRegister.class);
        if (colors.getMap().containsKey(modid))
            return colors.getMap().get(modid);
        return 0;
    }

}
