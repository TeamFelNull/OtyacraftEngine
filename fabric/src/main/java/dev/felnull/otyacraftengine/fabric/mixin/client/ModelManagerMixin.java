package dev.felnull.otyacraftengine.fabric.mixin.client;

import net.minecraft.client.resources.model.ModelManager;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ModelManager.class)
public class ModelManagerMixin {
    /*@Inject(method = "apply(Lnet/minecraft/client/resources/model/ModelBakery;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V", at = @At(value = "INVOKE", target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"))
    private void apply(ModelBakery modelBakery, ResourceManager resourceManager, ProfilerFiller profilerFiller, CallbackInfo ci) {
        OEModelLoader.getInstance().load(modelBakery);
    }*/
}
