package red.felnull.otyacraftengine.client.util;

import net.minecraft.client.Minecraft;
import red.felnull.otyacraftengine.core.DeobfNames;
import red.felnull.otyacraftengine.core.lib.ObfuscationReflectionUtil;

public class ClientUtil {
    public static int getFps() {
        return ObfuscationReflectionUtil.getPrivateValue(Minecraft.class, DeobfNames.debugFPS);
    }
}
