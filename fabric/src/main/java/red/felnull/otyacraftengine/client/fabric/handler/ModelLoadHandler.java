package red.felnull.otyacraftengine.client.fabric.handler;

import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelResourceProvider;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import red.felnull.otyacraftengine.api.register.OEModelLoaderPathRegister;
import red.felnull.otyacraftengine.api.register.OERegistries;
import red.felnull.otyacraftengine.client.fabric.model.OBJLoader;

public class ModelLoadHandler implements ModelResourceProvider {

    public static void init() {
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(n -> new ModelLoadHandler());
    }

    @Override
    public @Nullable UnbakedModel loadModelResource(ResourceLocation resourceId, ModelProviderContext context) {
        if (OERegistries.getRegistry(OEModelLoaderPathRegister.class).getSet().contains(resourceId.getNamespace())) {
            return OBJLoader.getInstance().loadModel(new ResourceLocation(resourceId.getNamespace(), "models/" + resourceId.getPath()));
        }
        return null;
    }
}
