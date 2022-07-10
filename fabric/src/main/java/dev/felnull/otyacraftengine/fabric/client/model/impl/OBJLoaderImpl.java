package dev.felnull.otyacraftengine.fabric.client.model.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.javagl.obj.ObjReader;
import de.javagl.obj.ObjUtils;
import dev.felnull.otyacraftengine.fabric.client.model.OBJLoader;
import dev.felnull.otyacraftengine.fabric.client.model.OBJOption;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OBJLoaderImpl implements OBJLoader {
    public static OBJLoader INSTANCE = new OBJLoaderImpl();
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(ItemTransforms.class, new ItemTransforms.Deserializer()).registerTypeAdapter(ItemTransform.class, new ItemTransform.Deserializer()).create();
    private static final String LOADER_ID = "forge:obj";

    @Override
    public @Nullable UnbakedModel loadModel(@NotNull ResourceManager resourceManager, @NotNull ResourceLocation location) throws IOException {
        ResourceLocation modelPath = new ResourceLocation(location.getNamespace(), location.getPath() + ".json");
        Optional<JsonElement> loader = getRootElement(resourceManager, modelPath, "loader");
        return loader.map(elm -> {
            if (elm.isJsonPrimitive() && elm.getAsJsonPrimitive().isString() && LOADER_ID.equals(elm.getAsString())) {
                try {
                    Optional<JsonElement> model = getRootElement(resourceManager, modelPath, "model");
                    return model.map(mname -> {
                        if (mname.isJsonPrimitive() && mname.getAsJsonPrimitive().isString()) {
                            try {
                                Optional<JsonElement> display = getRootElement(resourceManager, modelPath, "display");
                                var option = OBJOption.of(str -> {
                                    try {
                                        return getRootElement(resourceManager, modelPath, str);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                });
                                return loadModel(resourceManager, new ResourceLocation(mname.getAsString()), GSON.fromJson(display.orElseGet(JsonObject::new), ItemTransforms.class), option);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        return null;
                    }).orElse(null);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return null;
        }).orElse(null);
    }

    @Override
    public @Nullable UnbakedModel loadModel(@NotNull ResourceManager resourceManager, @NotNull ResourceLocation location, @NotNull ItemTransforms transforms, @NotNull OBJOption option) throws IOException {
        return resourceManager.getResource(location).map(res -> {
            try (InputStream stream = res.open()) {
                var obj = ObjUtils.convertToRenderable(ObjReader.read(stream));
                return new OBJUnbakedModelModel(obj, loadMTL(resourceManager, location, obj.getMtlFileNames()), transforms, option);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).orElse(null);
    }

    private Map<String, OBJMtl> loadMTL(ResourceManager resourceManager, ResourceLocation modelLocation, List<String> mtlNames) throws IOException {
        Map<String, OBJMtl> mtls = new LinkedHashMap<>();
        for (String name : mtlNames) {
            var pths = modelLocation.getPath().split("/");
            pths[pths.length - 1] = name;
            var pth = String.join("/", pths);
            ResourceLocation resourceId = new ResourceLocation(modelLocation.getNamespace(), pth);
            resourceManager.getResource(resourceId).ifPresent(res -> {
                try (BufferedReader reader = res.openAsReader()) {
                    OBJMtlReader.read(reader).forEach(mtl -> mtls.put(mtl.getName(), mtl));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        return mtls;
    }

    private Optional<JsonElement> getRootElement(ResourceManager resourceManager, ResourceLocation modelPath, String name) throws IOException {
        while (modelPath != null) {
            var res = resourceManager.getResource(modelPath);

            if (res.isEmpty()) return Optional.empty();

            try (InputStream stream = res.get().open(); InputStreamReader reader = new InputStreamReader(stream)) {
                var rawModel = GsonHelper.parse(reader);

                if (rawModel.has(name)) return Optional.ofNullable(rawModel.get(name));

                if (rawModel.has("parent")) {
                    var modelId = new ResourceLocation(rawModel.get("parent").getAsString());
                    modelPath = new ResourceLocation(modelId.getNamespace(), "models/" + modelId.getPath() + ".json");
                } else {
                    modelPath = null;
                }
            }
        }
        return Optional.empty();
    }
}
