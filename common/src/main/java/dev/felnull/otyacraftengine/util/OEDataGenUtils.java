package dev.felnull.otyacraftengine.util;

import dev.felnull.otyacraftengine.explatform.OEDataGenExpectPlatform;

/**
 * Data生成関係
 *
 * @author MORIMORI0317
 */
public class OEDataGenUtils {
    /**
     * Data生成中かどうか
     *
     * @return 生成中かどうか
     */
    public static boolean isDataGenerating() {
        return OEDataGenExpectPlatform.isDataGenerating();
    }
}
