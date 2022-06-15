package dev.felnull.otyacraftengine.explatform.client.fabric;

import com.mojang.blaze3d.platform.InputConstants;
import dev.felnull.otyacraftengine.client.shape.ClientIVShapeManager;
import dev.felnull.otyacraftengine.fabric.client.shape.ClientIVShapeManagerFabric;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;

public class OEClientExpectPlatformImpl {
    public static InputConstants.Key getKey(KeyMapping key) {
        return KeyBindingHelper.getBoundKeyOf(key);
    }

    public static ClientIVShapeManager createCIVSManagerInstance() {
        return new ClientIVShapeManagerFabric();
    }
}
