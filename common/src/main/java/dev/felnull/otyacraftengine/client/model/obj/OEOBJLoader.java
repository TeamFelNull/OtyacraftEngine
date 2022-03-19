package dev.felnull.otyacraftengine.client.model.obj;

import com.google.common.collect.ImmutableSet;
import de.javagl.obj.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.inventory.InventoryMenu;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.nio.file.Paths;
import java.util.*;

public class OEOBJLoader {
    private static final Logger LOGGER = LogManager.getLogger(OEOBJLoader.class);
    private static final OEOBJLoader INSTANCE = new OEOBJLoader();

    public static OEOBJLoader getInstance() {
        return INSTANCE;
    }

    public UnbakedModel load(ResourceManager manager, ResourceLocation id, ItemTransforms transforms) {
        Obj obj = null;
        Map<String, List<OEMtl>> mtls = new HashMap<>();
        try (InputStream stream = manager.getResource(id).getInputStream()) {
            obj = ObjUtils.convertToRenderable(ObjReader.read(stream));
            for (String mtlFileName : obj.getMtlFileNames()) {
                var mtll = loadMtl(manager, id, mtlFileName);
                mtls.put(mtll.get(0).getName(), mtll);
            }
        } catch (Exception e) {
            LOGGER.error("Error occurred while loading resource obj" + id.toString(), e);
        }
        return load(obj, mtls, transforms);
    }

    public UnbakedModel load(Obj obj, Map<String, List<OEMtl>> mtls, ItemTransforms transforms) {
        return new OEOBJUnbakedModel(obj, mtls, transforms);
    }

    private List<OEMtl> loadMtl(ResourceManager manager, ResourceLocation objLocation, String mtlFileName) {
        List<OEMtl> mtls = new ArrayList<>();
        var rl = new ResourceLocation(objLocation.getNamespace(), Paths.get(objLocation.getPath()).getParent().resolve(mtlFileName).toString().replace("\\", "/"));
        if (manager.hasResource(rl)) {
            try (InputStream stream = manager.getResource(rl).getInputStream()) {
                mtls.addAll(OEMtl.read(stream));
            } catch (Exception e) {
                LOGGER.error("Error occurred while loading resource mtl" + rl, e);
            }
        }
        return mtls;
    }

    public Set<ResourceLocation> getLoadTextures(TextureAtlas atlas) {
        if (atlas.location().equals(InventoryMenu.BLOCK_ATLAS)) {
            Set<ResourceLocation> texs = new HashSet<>();
            var manager = Minecraft.getInstance().getResourceManager();
            for (ResourceLocation id : manager.listResources("oe_models", path -> path.endsWith(".mtl"))) {
                try (InputStream stream = manager.getResource(id).getInputStream()) {
                    var mtl = MtlReader.read(stream);
                    for (Mtl m : mtl) {
                        texs.add(new ResourceLocation(m.getMapKd()));
                    }
                } catch (Exception ex) {
                    LOGGER.error("Error occurred while loading resource mtl texture" + id, ex);
                }
            }
            return texs;
        }
        return ImmutableSet.of();
    }

}
