package dev.felnull.otyacraftengine.client.model.obj;

import com.mojang.datafixers.util.Pair;
import de.javagl.obj.Mtl;
import de.javagl.obj.Obj;
import de.javagl.obj.ObjUtils;
import dev.felnull.otyacraftengine.impl.client.OEClientExpectPlatform;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;

public class OEOBJUnbakedModel implements UnbakedModel {
    private final Obj obj;
    private final Map<String, List<OEMtl>> mtls;
    private final ItemTransforms transforms;

    public OEOBJUnbakedModel(Obj obj, Map<String, List<OEMtl>> mtls, ItemTransforms transforms) {
        this.obj = ObjUtils.triangulate(obj);
        this.mtls = mtls;
        this.transforms = transforms;
    }

    @Override
    public Collection<ResourceLocation> getDependencies() {
        return Collections.emptySet();
    }

    @Override
    public Collection<Material> getMaterials(Function<ResourceLocation, UnbakedModel> function, Set<Pair<String, String>> set) {
        List<Material> materials = new ArrayList<>();
        mtls.values().stream().flatMap(Collection::stream).forEach(n -> materials.add(new Material(InventoryMenu.BLOCK_ATLAS, new ResourceLocation(n.getMapKd()))));
        return materials;
    }

    @Nullable
    @Override
    public BakedModel bake(ModelBakery modelBakery, Function<Material, TextureAtlasSprite> function, ModelState modelState, ResourceLocation resourceLocation) {
        return createBakedModel(modelBakery, function, modelState, resourceLocation, obj, mtls, transforms);
    }

    private BakedModel createBakedModel(ModelBakery modelBakery, Function<Material, TextureAtlasSprite> function, ModelState modelState, ResourceLocation resourceLocation, Obj obj, Map<String, List<OEMtl>> mtls, ItemTransforms transforms) {
        return OEClientExpectPlatform.createOEOBJBakedModel(modelBakery, function, modelState, resourceLocation, obj, mtls, transforms);
    }
}
