package dev.felnull.otyacraftengine.fabric.client.handler;

import dev.felnull.otyacraftengine.api.event.client.FabricOBJLoaderEvent;
import dev.felnull.otyacraftengine.fabric.client.model.OBJLoader;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelResourceProvider;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class ModelResourceHandler implements ModelResourceProvider {
    public static void init() {
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(n -> new ModelResourceHandler());
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

}
