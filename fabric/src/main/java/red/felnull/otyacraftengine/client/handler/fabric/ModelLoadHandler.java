package red.felnull.otyacraftengine.client.handler.fabric;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelProviderException;
import net.fabricmc.fabric.api.client.model.ModelVariantProvider;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import org.jetbrains.annotations.Nullable;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.api.register.OERegistries;
import red.felnull.otyacraftengine.client.model.fabric.FabricOBJLoader;

import java.io.InputStreamReader;
import java.util.Optional;

public class ModelLoadHandler implements ModelVariantProvider {
    public static final Gson GSON = (new GsonBuilder()).registerTypeAdapter(ItemTransforms.class, new ItemTransforms.Deserializer()).registerTypeAdapter(ItemTransform.class, new ItemTransform.Deserializer()).create();
    private static final ResourceLocation PATH_LOC = new ResourceLocation(OtyacraftEngine.MODID, "model_loader_path");
    private final ResourceManager resourceManager;

    private ModelLoadHandler(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    public static void init() {
        ModelLoadingRegistry.INSTANCE.registerVariantProvider(ModelLoadHandler::new);
    }

    @Override
    public @Nullable UnbakedModel loadModelVariant(ModelResourceLocation modelId, ModelProviderContext context) throws ModelProviderException {
        if (OERegistries.getSingleRegistry(PATH_LOC).getSet().contains(modelId.getNamespace())) {
            ResourceLocation modelPath = new ResourceLocation(modelId.getNamespace(), "models/item/" + modelId.getPath() + ".json");
            Optional<JsonElement> loader = getRawModelDatas(modelPath, "loader");
            if (loader.isPresent() && "forge:obj".equals(loader.get().getAsString())) {
                Optional<JsonElement> model = getRawModelDatas(modelPath, "model");
                Optional<JsonElement> display = getRawModelDatas(modelPath, "display");
                ItemTransforms transforms = null;
                if (display.isPresent()) {
                    transforms = GSON.fromJson(display.get(), ItemTransforms.class);
                }
                if (model.isPresent()) {
                    return FabricOBJLoader.getInstance().loadModel(new ResourceLocation(model.get().getAsString()), transforms);
                }
            }
            return null;
        }
        return null;
    }

    private Optional<JsonElement> getRawModelDatas(ResourceLocation modelPath, String name) {
        try {
            JsonObject rawModel = null;
            while (modelPath != null) {
                if (!resourceManager.hasResource(modelPath))
                    return Optional.empty();

                InputStreamReader reader = new InputStreamReader(resourceManager.getResource(modelPath).getInputStream());
                rawModel = GsonHelper.parse(reader);

                if (rawModel.has(name))
                    return Optional.ofNullable(rawModel.get(name));

                if (rawModel.has("parent")) {
                    ResourceLocation modelId = new ResourceLocation(rawModel.get("parent").getAsString());
                    modelPath = new ResourceLocation(modelId.getNamespace(), "models/" + modelId.getPath() + ".json");
                } else {
                    modelPath = null;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }
}
