package red.felnull.otyacraftengine.client.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import red.felnull.otyacraftengine.client.impl.OEClientExpectPlatform;

public class IKSGClientUtil {
    public static boolean isKeyInput(KeyMapping key) {
        return InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), OEClientExpectPlatform.getKey(key).getValue());
    }
}
