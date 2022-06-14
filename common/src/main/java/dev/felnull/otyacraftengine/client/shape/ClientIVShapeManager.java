package dev.felnull.otyacraftengine.client.shape;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ClientIVShapeManager extends SimplePreparableReloadListener<ClientIVShapeLoader> {
    private static ClientIVShapeManager INSTANCE;
    private Map<ResourceLocation, VoxelClientShape> voxelClientShapes;
    private Map<ResourceLocation, VoxelClientShape> legacyVoxelClientShapes = new HashMap<>();

    public ClientIVShapeManager() {
        INSTANCE = this;
    }

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
}
