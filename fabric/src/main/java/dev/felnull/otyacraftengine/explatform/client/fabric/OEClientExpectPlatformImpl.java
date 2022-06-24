package dev.felnull.otyacraftengine.explatform.client.fabric;

import com.mojang.blaze3d.platform.InputConstants;
import dev.felnull.otyacraftengine.client.shape.ClientIVShapeManager;
import dev.felnull.otyacraftengine.fabric.client.shape.ClientIVShapeManagerFabric;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.model.BakedModelManagerHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;

public class OEClientExpectPlatformImpl {
    private static final Minecraft mc = Minecraft.getInstance();

    public static InputConstants.Key getKey(KeyMapping key) {
        return KeyBindingHelper.getBoundKeyOf(key);
    }

    public static ClientIVShapeManager createCIVSManagerInstance() {
        return new ClientIVShapeManagerFabric();
    }

    public static BakedModel getModel(ResourceLocation location) {
        return BakedModelManagerHelper.getModel(mc.getModelManager(), location);
    }
}
