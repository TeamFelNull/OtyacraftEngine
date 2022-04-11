package dev.felnull.otyacraftengine.fabric.mixin.client;

import dev.felnull.otyacraftengine.client.event.OEClientEventHooks;
import dev.monarkhes.myron.impl.client.obj.ObjLoader;
import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//https://github.com/Haven-King/myron/blob/1.6.0-lib/src/main/java/dev/monarkhes/myron/impl/client/obj/ObjLoader.java
@Mixin(ObjLoader.class)
public class MyronObjLoaderMixin {
    @Inject(method = "loadModelResource", at = @At("RETURN"), cancellable = true, remap = false)
    private void loadModelResource(ResourceLocation identifier, ModelProviderContext modelProviderContext, CallbackInfoReturnable<UnbakedModel> cir) {
        if (OEClientEventHooks.onFabricOBJLoader(identifier))
            cir.setReturnValue(null);
    }

    @Inject(method = "loadModelVariant", at = @At("RETURN"), cancellable = true, remap = false)
    private void loadModelVariant(ModelResourceLocation model, ModelProviderContext modelPath, CallbackInfoReturnable<UnbakedModel> cir) {
        if (OEClientEventHooks.onFabricOBJLoader(model))
            cir.setReturnValue(null);
    }
}
