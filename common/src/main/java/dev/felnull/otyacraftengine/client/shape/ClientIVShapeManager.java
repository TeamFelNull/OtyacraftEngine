package dev.felnull.otyacraftengine.client.shape;

import dev.felnull.otyacraftengine.explatform.client.OEClientExpectPlatform;
import dev.felnull.otyacraftengine.shape.VoxelEdge;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ClientIVShapeManager extends SimplePreparableReloadListener<ClientIVShapeLoader> {
    private static final ClientIVShapeManager INSTANCE = OEClientExpectPlatform.createCIVSManagerInstance();
    private Map<ResourceLocation, VoxelClientShape> voxelClientShapes;
    private Map<ResourceLocation, VoxelClientShape> legacyVoxelClientShapes = new HashMap<>();

    public static ClientIVShapeManager getInstance() {
        return INSTANCE;
    }

    @Override
    protected ClientIVShapeLoader prepare(@NotNull ResourceManager resourceManager, @NotNull ProfilerFiller profilerFiller) {
        return new ClientIVShapeLoader(resourceManager, profilerFiller);
    }

    @Override
    protected void apply(@NotNull ClientIVShapeLoader loader, @NotNull ResourceManager resourceManager, @NotNull ProfilerFiller profilerFiller) {
        voxelClientShapes = loader.getVoxelClientShapes();
    }

    @Nullable
    public VoxelClientShape getVoxelClientShape(@Nullable ResourceLocation location) {
        var ret = voxelClientShapes.get(location);
        if (ret != null)
            return ret;
        return legacyVoxelClientShapes.get(location);
    }

    public void addLegacyShapes(ResourceLocation location, Set<VoxelEdge> edges) {
        legacyVoxelClientShapes.put(location, new VoxelClientShape(null, null, edges));
    }
}
