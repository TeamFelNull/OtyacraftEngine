package red.felnull.otyacraftengine.client.fabric.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.javagl.obj.Obj;
import de.javagl.obj.ObjReader;
import de.javagl.obj.ObjUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;

import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//https://github.com/OnyxStudios/FOML <-参考
public class OBJLoader {
    private static final OBJLoader INSTANCE = new OBJLoader();
    public static final Gson GSON = (new GsonBuilder()).registerTypeAdapter(ItemTransforms.class, new ItemTransforms.Deserializer()).registerTypeAdapter(ItemTransform.class, new ItemTransform.Deserializer()).create();

    public static OBJLoader getInstance() {
        return INSTANCE;
    }


    public UnbakedModel loadModel(ResourceLocation location) {
        ResourceLocation modelPath = new ResourceLocation(location.getNamespace(), location.getPath() + ".json");
        Optional<JsonElement> loader = getRawModelDatas(modelPath, "loader");
        if (loader.isPresent() && "forge:obj".equals(loader.get().getAsString())) {
            Optional<JsonElement> model = getRawModelDatas(modelPath, "model");
            Optional<JsonElement> display = getRawModelDatas(modelPath, "display");
            ItemTransforms transforms = null;
            if (display.isPresent()) {
                transforms = GSON.fromJson(display.get(), ItemTransforms.class);
            } else {
                transforms = GSON.fromJson(new JsonObject(), ItemTransforms.class);
            }
            if (model.isPresent()) {
                return loadModel(new ResourceLocation(model.get().getAsString()), transforms);
            }
        }
        return null;
    }

    public UnbakedModel loadModel(ResourceLocation location, ItemTransforms transforms) {
        ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
        if (resourceManager.hasResource(location)) {
            try {
                InputStreamReader reader = new InputStreamReader(resourceManager.getResource(location).getInputStream());
                Obj obj = ObjUtils.convertToRenderable(ObjReader.read(reader));
                return new OBJUnbakedModelModel(obj, loadMTL(location, obj.getMtlFileNames()), transforms);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public Map<String, OBJMtlData> loadMTL(ResourceLocation modelLocation, List<String> mtlNames) throws Exception {
        Map<String, OBJMtlData> mtls = new LinkedHashMap<>();
        ResourceManager manager = Minecraft.getInstance().getResourceManager();
        for (String name : mtlNames) {
            ResourceLocation resourceId = new ResourceLocation(modelLocation.getNamespace(), Paths.get(modelLocation.getPath()).getParent().resolve(name).toString().replace("\\", "/"));
            if (manager.hasResource(resourceId)) {
                Resource resource = manager.getResource(resourceId);
                OBJMtlReader.read(resource.getInputStream()).forEach(mtl -> {
                    mtls.put(mtl.getName(), mtl);
                });
            }
        }
        return mtls;
    }

    private Optional<JsonElement> getRawModelDatas(ResourceLocation modelPath, String name) {
        ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
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
