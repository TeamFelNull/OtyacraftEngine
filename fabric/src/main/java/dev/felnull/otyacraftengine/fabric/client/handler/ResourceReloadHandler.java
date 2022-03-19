package dev.felnull.otyacraftengine.fabric.client.handler;

import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.client.model.OEModelLoader;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class ResourceReloadHandler {
    public static final ResourceLocation OE_3D_MODEL = new ResourceLocation(OtyacraftEngine.MODID, "3d_model");

    public static void init() {
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new SimpleResourceReloadListener<OEModelLoader>() {
            @Override
            public ResourceLocation getFabricId() {
                return OE_3D_MODEL;
            }

            @Override
            public CompletableFuture<OEModelLoader> load(ResourceManager manager, ProfilerFiller profiler, Executor executor) {
                return CompletableFuture.supplyAsync(() -> OEModelLoader.load(manager), executor);
            }

            @Override
            public CompletableFuture<Void> apply(OEModelLoader data, ResourceManager manager, ProfilerFiller profiler, Executor executor) {
                return CompletableFuture.runAsync(data::apply, executor);
            }
        });
    }
}
