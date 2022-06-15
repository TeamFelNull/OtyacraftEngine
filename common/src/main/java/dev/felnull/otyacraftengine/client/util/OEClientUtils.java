package dev.felnull.otyacraftengine.client.util;

import com.mojang.blaze3d.platform.InputConstants;
import dev.felnull.otyacraftengine.explatform.client.OEClientExpectPlatform;
import net.minecraft.client.KeyMapping;
import org.jetbrains.annotations.NotNull;

public class OEClientUtils {
    /**
     * 特定の時間でループする値
     *
     * @param loopTime 0.0から1.0までの時間
     * @return 0.0~1.0までの値
     */
    public static float getParSecond(long loopTime) {
        return (float) (System.currentTimeMillis() % loopTime) / (float) loopTime;
    }

    /**
     * keyMappingからkeyを取得
     *
     * @param keyMapping 対象
     * @return key
     */
    public static InputConstants.Key getKey(@NotNull KeyMapping keyMapping) {
        return OEClientExpectPlatform.getKey(keyMapping);
    }
}
