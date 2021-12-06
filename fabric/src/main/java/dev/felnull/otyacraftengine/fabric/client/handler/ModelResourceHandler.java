package dev.felnull.otyacraftengine.fabric.client.handler;

import dev.felnull.otyacraftengine.api.event.client.FabricOBJLoaderEvent;
import dev.felnull.otyacraftengine.fabric.client.model.OBJLoader;
import net.fabricmc.fabric.api.client.model.*;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class ModelResourceHandler implements ModelResourceProvider, ModelVariantProvider {
    public static void init() {
        var h = new ModelResourceHandler();
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(n -> h);
        ModelLoadingRegistry.INSTANCE.registerVariantProvider(n -> h);
    }

    @Override
    public @Nullable UnbakedModel loadModelResource(ResourceLocation resourceId, ModelProviderContext context) {
        try {
            if (FabricOBJLoaderEvent.LOAD.invoker().load(resourceId).isTrue())
                return OBJLoader.getInstance().loadModel(new ResourceLocation(resourceId.getNamespace(), "models/" + resourceId.getPath()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public @Nullable UnbakedModel loadModelVariant(ModelResourceLocation modelId, ModelProviderContext context) throws ModelProviderException {
        try {
            if (FabricOBJLoaderEvent.LOAD.invoker().load(modelId).isTrue())
                return OBJLoader.getInstance().loadModel(new ResourceLocation(modelId.getNamespace(), "models/" + modelId.getPath()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
