package dev.felnull.otyacraftengine;

import dev.felnull.fnjln.FelNullJavaLibraryNatives;
import dev.felnull.otyacraftengine.item.location.PlayerItemLocations;
import dev.felnull.otyacraftengine.networking.OEPackets;
import dev.felnull.otyacraftengine.server.data.OEWorldData;
import dev.felnull.otyacraftengine.server.handler.ServerHandler;
import dev.felnull.otyacraftengine.util.OEStringUtil;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class OtyacraftEngine {
    public static final Logger LOGGER = LogManager.getLogger(OtyacraftEngine.class);
    public static final OEConfig CONFIG = AutoConfig.register(OEConfig.class, Toml4jConfigSerializer::new).getConfig();
    public static final String MODID = "otyacraftengine";

    public static void init() {
        File libFile = new File(MODID);
        libFile.mkdirs();
        FelNullJavaLibraryNatives.init(libFile.toPath());

        LOGGER.info("\n" + OEStringUtil.getLogoASCIIArt());
        ServerHandler.init();
        OEPackets.init();
        OEWorldData.init();
        PlayerItemLocations.init();
        if (isTestMode())
            TestInit.init();
    }

    public static boolean isTestMode() {
        return CONFIG.testMode;
    }
}
