package dev.felnull.otyacraftengine.explatform.client.forge;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.VertexFormat;
import dev.felnull.otyacraftengine.client.shape.ClientIVShapeManager;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;

public class OEClientExpectPlatformImpl {
    private static final Minecraft mc = Minecraft.getInstance();

    public static InputConstants.Key getKey(KeyMapping key) {
        return key.getKey();
    }

    public static ClientIVShapeManager createCIVSManagerInstance() {
        return new ClientIVShapeManager();
    }

    public static BakedModel getModel(ResourceLocation location) {
        return mc.getModelManager().getModel(location);
    }
}
