package red.felnull.otyacraftengine.client.model.fabric;

import de.javagl.obj.Obj;
import de.javagl.obj.ObjReader;
import de.javagl.obj.ObjUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//https://github.com/OnyxStudios/FOML <-参考
public class FabricOBJLoader {
    private static final FabricOBJLoader INSTANCE = new FabricOBJLoader();

    public static FabricOBJLoader getInstance() {
        return INSTANCE;
    }

    public UnbakedModel loadModel(ResourceLocation location, ItemTransforms transforms) {
        ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
        if (resourceManager.hasResource(location)) {
            try {
                InputStreamReader reader = new InputStreamReader(resourceManager.getResource(location).getInputStream());
                Obj obj = ObjUtils.convertToRenderable(ObjReader.read(reader));
                return new FabricOBJModel(obj, loadMTL(location, obj.getMtlFileNames()), transforms);
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
}
