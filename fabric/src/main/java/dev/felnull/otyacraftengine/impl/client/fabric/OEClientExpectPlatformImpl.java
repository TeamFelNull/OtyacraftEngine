package dev.felnull.otyacraftengine.impl.client.fabric;

import com.mojang.blaze3d.platform.InputConstants;
import de.javagl.obj.Obj;
import dev.felnull.otyacraftengine.client.model.obj.OEMtl;
import dev.felnull.otyacraftengine.client.renderer.item.BEWLItemRenderer;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import dev.felnull.otyacraftengine.fabric.client.model.OEOBJModelFabric;
import net.fabricmc.fabric.api.client.model.BakedModelManagerHelper;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class OEClientExpectPlatformImpl {
    private static final Minecraft mc = Minecraft.getInstance();

    public static InputConstants.Key getKey(KeyMapping key) {
        return key.key;
    }

    public static void registerItemRenderer(ItemLike item, BEWLItemRenderer renderer) {
        BuiltinItemRendererRegistry.INSTANCE.register(item, (stack, mode, matrices, vertexConsumers, light, overlay) -> renderer.render(stack, mode, matrices, vertexConsumers, OERenderUtil.getPartialTicks(), light, overlay));
    }

    public static BakedModel getModel(ResourceLocation location) {
        return BakedModelManagerHelper.getModel(mc.getModelManager(), location);
    }

    public static BakedModel createOEOBJBakedModel(ModelBakery modelBakery, Function<Material, TextureAtlasSprite> function, ModelState modelState, ResourceLocation resourceLocation, Obj obj, Map<String, List<OEMtl>> mtls, ItemTransforms transforms) {
        return OEOBJModelFabric.create(modelBakery, function, modelState, resourceLocation, obj, mtls, transforms);
    }
}
