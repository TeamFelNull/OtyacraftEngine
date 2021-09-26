package dev.felnull.otyacraftengine;

import dev.felnull.otyacraftengine.util.OEStringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OtyacraftEngine {
    public static final Logger LOGGER = LogManager.getLogger(OtyacraftEngine.class);
    public static final String MODID = "otyacraftengine";

    public static void init() {
        LOGGER.info(OEStringUtil.getLogoASCIIArt());
    }
}
