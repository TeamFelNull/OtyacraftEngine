package dev.felnull.otyacraftengine.forge.mixin.client;


import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.mixin.client.ModelBakeryAccessor;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(ModelBakery.class)
public abstract class ModelBakeryMixin {
/*
    @Inject(method = "processLoading", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/model/ModelBakery;loadTopLevel(Lnet/minecraft/client/resources/model/ModelResourceLocation;)V", shift = At.Shift.AFTER, ordinal = 3))
    private void processLoading(ProfilerFiller arg, int i, CallbackInfo ci) {
        SpecialModelLoader.getInstance().load((ModelBakery) (Object) this);
    }*/

    @Inject(method = "loadModel", at = @At(value = "INVOKE", target = "Lnet/minecraft/resources/ResourceLocation;<init>(Ljava/lang/String;Ljava/lang/String;)V", ordinal = 2), cancellable = true)
    private void loadModel(ResourceLocation resourceLocation, CallbackInfo ci) throws Exception {
        ModelResourceLocation modelResourceLocation = (ModelResourceLocation) resourceLocation;
        if (Objects.equals(modelResourceLocation.getVariant(), OtyacraftEngine.MODID + "_default")) {
            ResourceLocation resourceLocation3 = new ResourceLocation(resourceLocation.getNamespace(), resourceLocation.getPath());
            BlockModel blockModel = ((ModelBakeryAccessor) this).loadBlockModelInvoker(resourceLocation3);
            ((ModelBakeryAccessor) this).cacheAndQueueDependenciesInvoker(modelResourceLocation, blockModel);
            ((ModelBakeryAccessor) this).getUnbakedCache().put(resourceLocation3, blockModel);
            ci.cancel();
        }
    }
}