package dev.felnull.otyacraftengine.mixin.client;

import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.io.IOException;
import java.util.Map;

@Mixin(ModelBakery.class)
public interface ModelBakeryAccessor {
    @Invoker(value = "loadTopLevel")
    void loadTopLevelInvoker(ModelResourceLocation modelResourceLocation);

    @Invoker(value = "loadBlockModel")
    BlockModel loadBlockModelInvoker(ResourceLocation arg) throws IOException;

    @Invoker(value = "cacheAndQueueDependencies")
    void cacheAndQueueDependenciesInvoker(ResourceLocation resourceLocation, UnbakedModel unbakedModel);

    @Accessor
    Map<ResourceLocation, UnbakedModel> getUnbakedCache();
}
