package dev.felnull.otyacraftengine.client.model;

import dev.felnull.otyacraftengine.client.gui.screen.debug.RenderTestScreen;
import dev.felnull.otyacraftengine.client.model.obj.OEOBJLoader;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

import java.util.HashMap;
import java.util.Map;

public class OEModelLoader {
    private static final Map<ResourceLocation, UnbakedModel> UNBAKED_MODELS = new HashMap<>();
    private static final Map<ResourceLocation, BakedModel> BAKED_MODELS = new HashMap<>();

    public OEModelLoader() {

    }

    public static OEModelLoader load(ResourceManager manager) {
        for (ResourceLocation id : manager.listResources("oe_models", path -> path.endsWith(".obj"))) {
            var ubm = OEOBJLoader.getInstance().load(manager, id, ItemTransforms.NO_TRANSFORMS);
            UNBAKED_MODELS.put(id, ubm);
        }
        return new OEModelLoader();
    }


    public void apply() {
        for (ResourceLocation resourceLocation : UNBAKED_MODELS.keySet()) {
            RenderTestScreen.addRenderTest((poseStack, multiBufferSource, f) -> {
                var model = BAKED_MODELS.get(resourceLocation);
                OERenderUtil.renderModel(poseStack, multiBufferSource.getBuffer(Sheets.cutoutBlockSheet()), model, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
            });
        }
    }

    public static void onBake(ModelBakery bakery) {
        ModelState state = BlockModelRotation.by(0, 0);
        UNBAKED_MODELS.forEach((n, m) -> BAKED_MODELS.put(n, m.bake(bakery, Material::sprite, state, n)));
    }
}
