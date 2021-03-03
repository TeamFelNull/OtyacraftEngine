package red.felnull.otyacraftengine.client.impl;

import com.mojang.blaze3d.platform.InputConstants.Key;
import me.shedaniel.architectury.annotations.ExpectPlatform;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.MouseHandler;

public class OEClientExpectPlatform {
    @ExpectPlatform
    public static boolean isMiddlePressed(MouseHandler mouseHelper) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Key getKey(KeyMapping key) {
        throw new AssertionError();
    }
}
