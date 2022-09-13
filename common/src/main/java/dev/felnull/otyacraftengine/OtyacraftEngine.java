package dev.felnull.otyacraftengine;

import dev.architectury.platform.Platform;
import dev.felnull.otyacraftengine.advancement.OECriteriaTriggers;
import dev.felnull.otyacraftengine.handler.CommonHandler;
import dev.felnull.otyacraftengine.item.location.PlayerItemLocations;
import dev.felnull.otyacraftengine.natives.fnjl.impl.FNJLNativesWrapperImpl;
import dev.felnull.otyacraftengine.networking.OEPackets;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OtyacraftEngine {
    public static final Logger LOGGER = LogManager.getLogger(OtyacraftEngine.class);
    public static final String MODID = "otyacraftengine";
    private static final OEConfig CONFIG = AutoConfig.register(OEConfig.class, PartitioningSerializer.wrap(Toml4jConfigSerializer::new)).getConfig();

    public static void init() {
        CommonHandler.init();
        OEPackets.init();
        OECriteriaTriggers.init();
        PlayerItemLocations.init();
        FNJLNativesWrapperImpl.init();
    }

    public static String getModName() {
        return Platform.getMod(MODID).getName();
    }

    public static OEConfig getConfig() {
        return CONFIG;
    }
}
