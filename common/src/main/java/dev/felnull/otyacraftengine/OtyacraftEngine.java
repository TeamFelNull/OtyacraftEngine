package dev.felnull.otyacraftengine;

import dev.felnull.fnjl.util.FNURLUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;

public class OtyacraftEngine {
    public static final Logger LOGGER = LogManager.getLogger(OtyacraftEngine.class);
    public static final String MODID = "otyacraftengine";

    public static void init() {
        LOGGER.info("ｳｧｧ!!ｵﾚﾓｲｯﾁｬｳｩｩｩ!!!ｳｳｳｳｳｳｳｳｳｩｩｩｩｩｩｩｩｳｳｳｳｳｳｳｳ!ｲｨｨｲｨｨｨｲｲｲｨｲｲｲｲ");

        try {
            var r = FNURLUtil.getResponse(new URL("https://www.morimori0317.net/inc-sounds-search/search?s=%E3%81%84%E3%81%8D%E3%81%99%E3%81%8E"));
            System.out.println(r);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
