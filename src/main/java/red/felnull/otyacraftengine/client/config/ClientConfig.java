package red.felnull.otyacraftengine.client.config;

import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;
import red.felnull.otyacraftengine.OtyacraftEngine;

public class ClientConfig {
    public static ConfigValue<Boolean> ToolTipModName;
    public static ConfigValue<Boolean> ToolTipTag;
    public static ConfigValue<Boolean> ToolTipDetailedInformation;

    public static void init() {
        Pair<ConfigLoder, ForgeConfigSpec> common_config = new ForgeConfigSpec.Builder().configure(ConfigLoder::new);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, common_config.getRight());
    }

    static class ConfigLoder {
        public ConfigLoder(ForgeConfigSpec.Builder builder) {
            OtyacraftEngine.LOGGER.info("Loading Client Config");
            builder.push("ToolTip");
            ToolTipModName = builder.define("ModName", true);
            ToolTipTag = builder.define("Tag", true);
            ToolTipDetailedInformation = builder.define("DetailedInformation", true);
            builder.pop();
        }
    }

}
