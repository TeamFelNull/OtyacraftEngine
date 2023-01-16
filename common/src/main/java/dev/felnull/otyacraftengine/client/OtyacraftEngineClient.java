package dev.felnull.otyacraftengine.client;

import dev.architectury.platform.Platform;
import dev.felnull.otyacraftengine.OEConfig;
import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.client.handler.ClientDebugHandler;
import dev.felnull.otyacraftengine.client.handler.ClientHandler;
import dev.felnull.otyacraftengine.client.renderer.texture.URLTextureManager;
import dev.felnull.otyacraftengine.client.shape.ClientIVShapeManager;
import dev.felnull.otyacraftengine.networking.OEPackets;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.gui.ConfigScreenProvider;

public class OtyacraftEngineClient {

    public static void init() {
        configInit();
        ClientDebugHandler.init();
        ClientHandler.init();
        URLTextureManager.getInstance().init();
        ClientIVShapeManager.getInstance().init();

        OEPackets.clientInit();
    }

    private static void configInit() {
        Platform.getMod(OtyacraftEngine.MODID).registerConfigurationScreen(parent -> {
            ConfigScreenProvider<OEConfig> provider = (ConfigScreenProvider<OEConfig>) AutoConfig.getConfigScreen(OEConfig.class, parent);

            provider.setBuildFunction(builder -> {
                builder.setGlobalized(true);
                builder.setGlobalizedExpanded(false);
                return builder.build();
            });

            return provider.get();
        });
    }
}
