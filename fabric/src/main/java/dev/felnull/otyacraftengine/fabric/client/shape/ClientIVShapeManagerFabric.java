package dev.felnull.otyacraftengine.fabric.client.shape;

import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.client.shape.ClientIVShapeLoader;
import dev.felnull.otyacraftengine.client.shape.ClientIVShapeManager;
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class ClientIVShapeManagerFabric extends ClientIVShapeManager implements SimpleResourceReloadListener<ClientIVShapeLoader> {
    public static final ResourceLocation VOXEL_SHAPE = new ResourceLocation(OtyacraftEngine.MODID, "voxel_shape");


    @Override
    public CompletableFuture<ClientIVShapeLoader> load(ResourceManager manager, ProfilerFiller profiler, Executor executor) {
        return CompletableFuture.supplyAsync(() -> prepare(manager, profiler), executor);
    }

    @Override
    public CompletableFuture<Void> apply(ClientIVShapeLoader data, ResourceManager manager, ProfilerFiller profiler, Executor executor) {
        return CompletableFuture.runAsync(() -> apply(data, manager, profiler), executor);
    }

    @Override
    public ResourceLocation getFabricId() {
        return VOXEL_SHAPE;
    }
}
