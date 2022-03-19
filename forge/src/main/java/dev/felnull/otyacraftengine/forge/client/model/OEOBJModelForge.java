package dev.felnull.otyacraftengine.forge.client.model;

import com.mojang.math.Vector3f;
import de.javagl.obj.FloatTuple;
import de.javagl.obj.Obj;
import de.javagl.obj.ObjSplitting;
import dev.felnull.otyacraftengine.client.model.obj.OEMtl;
import dev.felnull.otyacraftengine.client.model.obj.OEOBJModel;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.client.model.pipeline.BakedQuadBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class OEOBJModelForge {
    private static final FaceBakery FACE_BAKERY = new FaceBakery();

    public static OEOBJModel create(ModelBakery modelBakery, Function<Material, TextureAtlasSprite> function, ModelState modelState, ResourceLocation resourceLocation, Obj obj, Map<String, List<OEMtl>> mtls, ItemTransforms transforms) {
        List<BakedQuad> faces = new ArrayList<>();
        Map<String, Obj> materialGroups = ObjSplitting.splitByMaterialGroups(obj);

        for (Map.Entry<String, Obj> entry : materialGroups.entrySet()) {
            String matName = entry.getKey();
            Obj matGroupObj = entry.getValue();
            OEMtl mtl = mtls.get(matName).get(0);
            int color = -1;
            TextureAtlasSprite mtlSprite = function.apply(OEOBJModel.DEFAULT_SPRITE);
            if (mtl != null) {
                FloatTuple diffuseColor = mtl.getKd();
                if (mtl.useDiffuseColor()) {
                    color = 0xFF000000;
                    for (int i = 0; i < 3; ++i) {
                        color |= (int) (255 * diffuseColor.get(i)) << (16 - 8 * i);
                    }
                }
                mtlSprite = function.apply(new Material(InventoryMenu.BLOCK_ATLAS, new ResourceLocation(mtl.getMapKd())));
            }
            for (int i = 0; i < matGroupObj.getNumFaces(); i++) {
                var face = matGroupObj.getFace(i);
                for (int v = 0; v < face.getNumVertices(); v++) {
                    var ft = matGroupObj.getVertex(face.getVertexIndex(v));
                    var vertex = new Vector3f(ft.getX(), ft.getY(), ft.getZ());
                    var normal = matGroupObj.getNormal(face.getNormalIndex(v));
                //    System.out.println("ｳｧｧ!!ｵﾚﾓｲｯﾁｬｳｩｩｩ!!!ｳｳｳｳｳｳｳｳｳｩｩｩｩｩｩｩｩｳｳｳｳｳｳｳｳ!ｲｨｨｲｨｨｨｲｲｲｨｲｲｲｲ");
                }
            }
        }

        var spmtl = mtls.get("sprite");
        boolean noSprite = spmtl == null || spmtl.isEmpty();
        var material = mtls.size() > 0 ? new Material(InventoryMenu.BLOCK_ATLAS, new ResourceLocation((noSprite ? mtls.values().iterator().next().get(0) : spmtl.get(0)).getMapKd())) : OEOBJModel.DEFAULT_SPRITE;
        return new OEOBJModel(faces, transforms, function.apply(material));
    }

    private static BakedQuad createBQ(TextureAtlasSprite sprite, int tintIndex) {
        BakedQuadBuilder builder = new BakedQuadBuilder(sprite);
        builder.setQuadTint(tintIndex);

        return builder.build();
    }
}
