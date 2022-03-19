package dev.felnull.otyacraftengine.fabric.client.handler;

import dev.felnull.otyacraftengine.client.entrypoint.OEClientEntryPointManager;
import dev.felnull.otyacraftengine.client.event.OEClientEventHooks;
import dev.felnull.otyacraftengine.fabric.client.model.OBJLoader;
import net.fabricmc.fabric.api.client.model.*;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class ModelResourceHandler implements ModelResourceProvider, ModelVariantProvider, ExtraModelProvider {
    public static void init() {
        var h = new ModelResourceHandler();
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(n -> h);
        ModelLoadingRegistry.INSTANCE.registerVariantProvider(n -> h);
        ModelLoadingRegistry.INSTANCE.registerModelProvider(h);
    }

    @Override
    public @Nullable UnbakedModel loadModelResource(ResourceLocation resourceId, ModelProviderContext context) {
        try {
            if (OEClientEventHooks.onFabricOBJLoader(resourceId))
                return OBJLoader.getInstance().loadModel(new ResourceLocation(resourceId.getNamespace(), "models/" + resourceId.getPath()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public @Nullable UnbakedModel loadModelVariant(ModelResourceLocation modelId, ModelProviderContext context) {
        try {
            if (OEClientEventHooks.onFabricOBJLoader(modelId))
                return OBJLoader.getInstance().loadModel(new ResourceLocation(modelId.getNamespace(), "models/" + modelId.getPath()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void provideExtraModels(ResourceManager manager, Consumer<ResourceLocation> out) {
        OEClientEntryPointManager.getInstance().call().onModelRegistry(out);
    }
}
