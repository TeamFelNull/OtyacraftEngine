package dev.felnull.otyacraftengine.fabric.client.model.impl;

/*

public class OBJUnbakedModelModel implements UnbakedModel {
    public static final Material DEFAULT_SPRITE = new Material(InventoryMenu.BLOCK_ATLAS, null);
    private final Obj modelObj;
    private final Map<String, OBJMtl> mtls;
    private final ItemTransforms transforms;
    private final Material material;
    private final OBJOption option;

    public OBJUnbakedModelModel(Obj modelObj, Map<String, OBJMtl> mtls, ItemTransforms transforms, OBJOption option) {
        this.modelObj = ObjUtils.triangulate(modelObj);
        this.mtls = mtls;
        this.transforms = transforms;
        this.option = option;

        Mtl mtl = mtls.get("sprite");
        this.material = mtls.size() > 0 ? new Material(InventoryMenu.BLOCK_ATLAS, new ResourceLocation((mtl == null ? mtls.values().iterator().next() : mtl).getMapKd())) : DEFAULT_SPRITE;
    }

    @Override
    public Collection<ResourceLocation> getDependencies() {
        return Collections.emptySet();
    }

    @Override
    public Collection<Material> getMaterials(Function<ResourceLocation, UnbakedModel> function, Set<Pair<String, String>> set) {
        List<Material> sprites = new ArrayList<>();
        mtls.values().forEach(mtl -> sprites.add(new Material(InventoryMenu.BLOCK_ATLAS, new ResourceLocation(mtl.getMapKd()))));
        return sprites;
    }

    @Nullable
    @Override
    public BakedModel bake(ModelBakery modelBakery, Function<Material, TextureAtlasSprite> function, ModelState modelState, ResourceLocation resourceLocation) {
        Renderer renderer = RendererAccess.INSTANCE.getRenderer();
        if (renderer == null)
            renderer = IndigoRenderer.INSTANCE;

        Map<String, Obj> materialGroups = ObjSplitting.splitByMaterialGroups(modelObj);
        MeshBuilder builder = renderer.meshBuilder();
        QuadEmitter emitter = builder.getEmitter();

        for (Map.Entry<String, Obj> entry : materialGroups.entrySet()) {
            String matName = entry.getKey();
            Obj matGroupObj = entry.getValue();
            OBJMtl mtl = mtls.get(matName);
            int color = -1;
            TextureAtlasSprite mtlSprite = function.apply(DEFAULT_SPRITE);
            if (mtl != null) {
                FloatTuple diffuseColor = mtl.getKd();
                if (mtl.isUseDiffuseColor()) {
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
                    addVertex(i, v, vertex, normal, emitter, matGroupObj, false, modelState);
                    if (v == 2 && matGroupObj.getFace(i).getNumVertices() == 3) {
                        addVertex(i, 3, vertex, normal, emitter, matGroupObj, true, modelState);
                    }
                }
                emitter.spriteColor(0, color, color, color, color);
                emitter.material(renderer.materialFinder().find());
                if (mtl != null)
                    emitter.colorIndex(mtl.getTintIndex());
                emitter.nominalFace(emitter.lightFace());
                emitter.spriteBake(0, mtlSprite, MutableQuadView.BAKE_NORMALIZED | (modelState.isUvLocked() ? MutableQuadView.BAKE_LOCK_UV : 0));

                emitter.emit();
            }
        }
        var mesh = builder.build();
        return new OBJModel(mesh, transforms, function.apply(material));
    }

    private void addVertex(int faceIndex, int vertIndex, Vector3f vertex, FloatTuple normal, QuadEmitter emitter, Obj matGroup, boolean degenerate, ModelState modelState) {
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

            if (modelObj.getNumTexCoords() > 0) {
                FloatTuple text = matGroup.getTexCoord(matGroup.getFace(faceIndex).getTexCoordIndex(textureCoordIndex));
                float v = text.getY();
                if (option.isFlipV())
                    v = 1f - v;
                emitter.sprite(vertIndex, 0, text.getX(), v);
            } else {
                emitter.nominalFace(Direction.getNearest(normal.getX(), normal.getY(), normal.getZ()));
            }
        } catch (Exception ignored) {
        }
    }
}
*/
