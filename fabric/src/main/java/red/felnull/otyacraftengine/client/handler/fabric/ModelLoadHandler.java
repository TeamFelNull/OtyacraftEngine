package red.felnull.otyacraftengine.client.handler.fabric;

import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelResourceProvider;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.api.register.OERegistries;
import red.felnull.otyacraftengine.client.model.fabric.FabricOBJLoader;

public class ModelLoadHandler implements ModelResourceProvider {
    private static final ResourceLocation PATH_LOC = new ResourceLocation(OtyacraftEngine.MODID, "model_loader_path");

    public static void init() {
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(n -> new ModelLoadHandler());
    }

    @Override
    public @Nullable UnbakedModel loadModelResource(ResourceLocation resourceId, ModelProviderContext context) {
        if (OERegistries.getSingleRegistry(PATH_LOC).getSet().contains(resourceId.getNamespace())) {
            return FabricOBJLoader.getInstance().loadModel(new ResourceLocation(resourceId.getNamespace(), "models/" + resourceId.getPath()));
        }
        return null;
    }
}
