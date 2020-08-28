package red.felnull.otyacraftengine.client.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;
import red.felnull.otyacraftengine.OtyacraftEngine;

public class ClientConfig {
    public static ConfigValue<Boolean> ToolTipModName;
    public static ConfigValue<Boolean> ToolTipTag;
    public static ConfigValue<Boolean> ToolTipDetailedInformation;
    public static ConfigValue<String> ToolTipTagKey;
    public static ConfigValue<String> ToolTipDetailedInformationKey;
    public static ConfigValue<Boolean> DeleteUnnecessaryTextureCash;
    public static ConfigValue<Boolean> BeaconOverlay;

    public static void init() {
        Pair<ConfigLoder, ForgeConfigSpec> common_config = new ForgeConfigSpec.Builder().configure(ConfigLoder::new);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, common_config.getRight());
    }

    static class ConfigLoder {
        public ConfigLoder(ForgeConfigSpec.Builder builder) {
            OtyacraftEngine.LOGGER.info("Loading Client Config");
            builder.push("ToolTip");
            ToolTipModName = builder.define("Show ModName", true);
            ToolTipTag = builder.define("Show Tag", true);
            ToolTipTagKey = builder.define("Show Tag Key", "key.sprint");
            ToolTipDetailedInformation = builder.define("Show DetailedInformation", true);
            ToolTipDetailedInformationKey = builder.define("Show DetailedInformation Key", "key.sneak");
            builder.pop();
            builder.push("Data");
            DeleteUnnecessaryTextureCash = builder.define("Delete Unnecessary Texture Cash at startup", true);
            builder.pop();
            builder.push("GUI");
            BeaconOverlay = builder.define("Beacon Overlay", true);
            builder.pop();
        }
    }

}
