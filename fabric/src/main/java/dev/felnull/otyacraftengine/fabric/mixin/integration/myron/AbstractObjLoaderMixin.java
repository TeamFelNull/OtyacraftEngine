package dev.felnull.otyacraftengine.fabric.mixin.integration.myron;

/*

@Mixin(AbstractObjLoader.class)
public class AbstractObjLoaderMixin {
    @Inject(method = "loadModel", at = @At("RETURN"), cancellable = true, remap = false)
    private void loadModel(ResourceManager resourceManager, ResourceLocation identifier, ItemTransforms transformation, boolean isSideLit, CallbackInfoReturnable<UnbakedModel> cir) {
        if (OEClientEventHooks.onOBJLoaderCheck(identifier))
            cir.setReturnValue(null);
    }
}
*/
