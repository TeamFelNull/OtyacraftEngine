package dev.felnull.otyacraftengine.fabric.client.handler;

import dev.felnull.otyacraftengine.client.callpoint.ClientCallPointManager;
import dev.felnull.otyacraftengine.client.event.OEClientEventHooks;
import dev.felnull.otyacraftengine.fabric.client.model.OBJLoader;
import net.fabricmc.fabric.api.client.model.*;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class ModelResourceHandler implements ModelResourceProvider, ModelVariantProvider, ExtraModelProvider {
    public static final Logger LOGGER = LogManager.getLogger(ModelResourceHandler.class);
    private final ResourceManager resourceManager;

    public ModelResourceHandler(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    public static void init() {
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(ModelResourceHandler::new);
        ModelLoadingRegistry.INSTANCE.registerVariantProvider(ModelResourceHandler::new);
        ModelLoadingRegistry.INSTANCE.registerModelProvider(new ModelResourceHandler(null));
    }

    @Override
    public void provideExtraModels(ResourceManager manager, Consumer<ResourceLocation> out) {
        ClientCallPointManager.getInstance().call().onModelRegistry(out::accept);
    }

    @Override
    public @Nullable UnbakedModel loadModelResource(ResourceLocation resourceId, ModelProviderContext context) {
        if (OEClientEventHooks.onOBJLoaderCheck(resourceId)) {
            try {
                return OBJLoader.getInstance().loadModel(resourceManager, new ResourceLocation(resourceId.getNamespace(), "models/" + resourceId.getPath()));
            } catch (Exception e) {
                LOGGER.error("Error occurred while loading obj model resource " + resourceId, e);
            }
        }
        return null;
    }

    @Override
    public @Nullable UnbakedModel loadModelVariant(ModelResourceLocation modelId, ModelProviderContext context) {
        if (OEClientEventHooks.onOBJLoaderCheck(modelId)) {
            try {
                return OBJLoader.getInstance().loadModel(resourceManager, new ResourceLocation(modelId.getNamespace(), "models/" + modelId.getPath()));
            } catch (Exception e) {
                LOGGER.error("Error occurred while loading obj model resource " + modelId, e);
            }
        }
        return null;
    }
}
