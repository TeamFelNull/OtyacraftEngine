package dev.felnull.otyacraftengine.impl.client;

import com.mojang.blaze3d.platform.InputConstants;
import de.javagl.obj.Obj;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.felnull.otyacraftengine.client.model.obj.OEMtl;
import dev.felnull.otyacraftengine.client.renderer.item.BEWLItemRenderer;
import net.minecraft.client.KeyMapping;
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

public class OEClientExpectPlatform {

    @ExpectPlatform
    public static InputConstants.Key getKey(KeyMapping key) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerItemRenderer(ItemLike item, BEWLItemRenderer renderer) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static BakedModel getModel(ResourceLocation location) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static BakedModel createOEOBJBakedModel(ModelBakery modelBakery, Function<Material, TextureAtlasSprite> function, ModelState modelState, ResourceLocation resourceLocation, Obj obj, Map<String, List<OEMtl>> mtls, ItemTransforms transforms) {
        throw new AssertionError();
    }
}
