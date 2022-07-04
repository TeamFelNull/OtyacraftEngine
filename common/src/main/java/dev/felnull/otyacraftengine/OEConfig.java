package dev.felnull.otyacraftengine;

import dev.architectury.platform.Platform;
import dev.felnull.otyacraftengine.client.debug.HighlightVoxelShapeType;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.gui.ConfigScreenProvider;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;

@Config(name = OtyacraftEngine.MODID)
@Config.Gui.Background(OtyacraftEngine.MODID + ":textures/gui/config_background.png")
public class OEConfig extends PartitioningSerializer.GlobalData {
    public static void clientInit() {
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

    @ConfigEntry.Category("client")
    @ConfigEntry.Gui.TransitiveObject
    private ClientConfig clientConfig = new ClientConfig();

    @ConfigEntry.Category("client_debug")
    @ConfigEntry.Gui.TransitiveObject
    private DebugConfig debugConfig = new DebugConfig();

    public DebugConfig getDebugConfig() {
        return debugConfig;
    }

    public ClientConfig getClientConfig() {
        return clientConfig;
    }

    @Config(name = "client_debug")
    public static class DebugConfig implements ConfigData {
        private HighlightVoxelShapeType highlightVoxelShape = HighlightVoxelShapeType.OFF;

        private boolean showTagTooltip = false;

        private boolean showModNameTooltip = false;

        private boolean showWidgetData = false;

        public HighlightVoxelShapeType getHighlightVoxelShape() {
            return highlightVoxelShape;
        }

        public boolean isShowWidgetData() {
            return showWidgetData;
        }

        public boolean isShowTagTooltip() {
            return showTagTooltip;
        }

        public boolean isShowModNameTooltip() {
            return showModNameTooltip;
        }
    }

    @Config(name = "client")
    public static class ClientConfig implements ConfigData {
        @ConfigEntry.Gui.CollapsibleObject
        private URLTextureConfig urlTextureConfig = new URLTextureConfig();

        public URLTextureConfig getUrlTextureConfig() {
            return urlTextureConfig;
        }

        public static class URLTextureConfig {
            private int maxLoaderCount = 3;

            private String urlRegex = "https://(i.imgur.com|imgur.com)/.*";

            public int getMaxLoaderCount() {
                return Math.max(1, maxLoaderCount);
            }

            public String getUrlRegex() {
                return urlRegex;
            }
        }
    }
}
