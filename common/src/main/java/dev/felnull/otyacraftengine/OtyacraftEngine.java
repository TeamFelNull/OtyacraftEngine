package dev.felnull.otyacraftengine;

import dev.architectury.platform.Platform;
import dev.felnull.otyacraftengine.advancement.OECriteriaTriggers;
import dev.felnull.otyacraftengine.handler.CommonHandler;
import dev.felnull.otyacraftengine.networking.OEPackets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OtyacraftEngine {
    public static final Logger LOGGER = LogManager.getLogger(OtyacraftEngine.class);
    public static final String MODID = "otyacraftengine";

    public static void init() {
        CommonHandler.init();
        OEPackets.init();
        OECriteriaTriggers.init();
    }

    public static String getModName() {
        return Platform.getMod(MODID).getName();
    }
}
