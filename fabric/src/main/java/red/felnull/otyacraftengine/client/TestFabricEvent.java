package red.felnull.otyacraftengine.client;

import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelResourceProvider;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import red.felnull.otyacraftengine.client.model.fabric.FabricOBJLoader;


public class TestFabricEvent implements ModelResourceProvider {

    public static void init() {
     //   ModelLoadingRegistry.INSTANCE.registerResourceProvider(n -> new TestFabricEvent());
    }


    @Override
    public @Nullable UnbakedModel loadModelResource(ResourceLocation resourceId, ModelProviderContext context) {
        return FabricOBJLoader.getInstance().loadModel(new ResourceLocation(resourceId.getNamespace(), "models/" + resourceId.getPath()));
    }
}
