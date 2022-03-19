package dev.felnull.otyacraftengine.fabric.client.model;

import com.mojang.math.Transformation;
import com.mojang.math.Vector3f;
import de.javagl.obj.FloatTuple;
import de.javagl.obj.Obj;
import de.javagl.obj.ObjSplitting;
import dev.felnull.otyacraftengine.client.model.obj.OEMtl;
import dev.felnull.otyacraftengine.client.model.obj.OEOBJModel;
import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.model.ModelHelper;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

public class OEOBJModelFabric extends OEOBJModel implements FabricBakedModel {
    private final Mesh mesh;

    public OEOBJModelFabric(Mesh mesh, List<BakedQuad> faces, ItemTransforms transforms, TextureAtlasSprite particleSprite) {
        super(faces, transforms, particleSprite);
        this.mesh = mesh;
    }

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }

    @Override
    public void emitBlockQuads(BlockAndTintGetter blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
        if (mesh != null)
            context.meshConsumer().accept(mesh);
    }

    @Override
    public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context) {
        if (mesh != null)
            context.meshConsumer().accept(mesh);
    }

    public static OEOBJModelFabric create(ModelBakery modelBakery, Function<Material, TextureAtlasSprite> function, ModelState modelState, ResourceLocation resourceLocation, Obj obj, Map<String, List<OEMtl>> mtls, ItemTransforms transforms) {
        Renderer renderer = RendererAccess.INSTANCE.getRenderer();
        Mesh mesh = null;

        if (renderer != null) {
            Map<String, Obj> materialGroups = ObjSplitting.splitByMaterialGroups(obj);
            MeshBuilder builder = renderer.meshBuilder();
            QuadEmitter emitter = builder.getEmitter();
            for (Map.Entry<String, Obj> entry : materialGroups.entrySet()) {
                String matName = entry.getKey();
                Obj matGroupObj = entry.getValue();
                OEMtl mtl = mtls.get(matName).get(0);
                int color = -1;
                TextureAtlasSprite mtlSprite = function.apply(DEFAULT_SPRITE);
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
                    FloatTuple floatTuple;
                    Vector3f vertex;
                    FloatTuple normal;
                    int v;
                    for (v = 0; v < matGroupObj.getFace(i).getNumVertices(); v++) {
                        floatTuple = matGroupObj.getVertex(matGroupObj.getFace(i).getVertexIndex(v));
                        vertex = new Vector3f(floatTuple.getX(), floatTuple.getY(), floatTuple.getZ());
                        normal = matGroupObj.getNormal(matGroupObj.getFace(i).getNormalIndex(v));
                        addVertex(obj, i, v, vertex, normal, emitter, matGroupObj, false, modelState);
                        if (v == 2 && matGroupObj.getFace(i).getNumVertices() == 3) {
                            addVertex(obj, i, 3, vertex, normal, emitter, matGroupObj, true, modelState);
                        }
                    }
                    emitter.spriteColor(0, color, color, color, color);
                    emitter.material(RendererAccess.INSTANCE.getRenderer().materialFinder().find());
                    emitter.colorIndex(mtl.getTintIndex());
                    emitter.nominalFace(emitter.lightFace());
                    emitter.spriteBake(0, mtlSprite, MutableQuadView.BAKE_NORMALIZED | (modelState.isUvLocked() ? MutableQuadView.BAKE_LOCK_UV : 0));
                    emitter.emit();
                }
            }
            mesh = builder.build();
        }
        var qs = ModelHelper.toQuadLists(mesh);
        var spmtl = mtls.get("sprite");
        boolean noSprite = spmtl == null || spmtl.isEmpty();
        var material = mtls.size() > 0 ? new Material(InventoryMenu.BLOCK_ATLAS, new ResourceLocation((noSprite ? mtls.values().iterator().next().get(0) : spmtl.get(0)).getMapKd())) : DEFAULT_SPRITE;
        return new OEOBJModelFabric(mesh, qs[ModelHelper.NULL_FACE_ID], transforms, function.apply(material));
    }

    private static void addVertex(Obj obj, int faceIndex, int vertIndex, Vector3f vertex, FloatTuple normal, QuadEmitter emitter, Obj matGroup, boolean degenerate, ModelState modelState) {
        try {
            int textureCoordIndex = vertIndex;
            if (degenerate)
                textureCoordIndex--;

            if (modelState.getRotation() != Transformation.identity() && !degenerate) {
                vertex.add(-0.5F, -0.5F, -0.5F);
                vertex.transform(modelState.getRotation().getLeftRotation());
                vertex.add(0.5f, 0.5f, 0.5f);
            }

            emitter.pos(vertIndex, vertex.x(), vertex.y(), vertex.z());
            emitter.normal(vertIndex, normal.getX(), normal.getY(), normal.getZ());

            if (obj.getNumTexCoords() > 0) {
                FloatTuple text = matGroup.getTexCoord(matGroup.getFace(faceIndex).getTexCoordIndex(textureCoordIndex));

                emitter.sprite(vertIndex, 0, text.getX(), text.getY());
            }
        } catch (Exception ignored) {
        }
    }
}
