package dev.felnull.otyacraftengine;

import dev.felnull.otyacraftengine.block.TestBlock;
import dev.felnull.otyacraftengine.blockentity.TestBlockEntity;
import dev.felnull.otyacraftengine.item.TestItem;
import dev.felnull.otyacraftengine.net.OEPackets;
import dev.felnull.otyacraftengine.util.OEStringUtil;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OtyacraftEngine {
    public static final Logger LOGGER = LogManager.getLogger(OtyacraftEngine.class);
    public static final OEConfig CONFIG = AutoConfig.register(OEConfig.class, Toml4jConfigSerializer::new).getConfig();
    public static final String MODID = "otyacraftengine";

    public static void init() {
        LOGGER.info(OEStringUtil.getLogoASCIIArt());
        OEPackets.init();
        if (CONFIG.testMode)
            testInit();
    }

    public static void testInit() {
        TestItem.init();
        TestBlock.init();
        TestBlockEntity.init();
    }

}
