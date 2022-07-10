package dev.felnull.otyacraftengine.fabric.client.model;

import dev.felnull.otyacraftengine.fabric.client.model.impl.OBJLoaderImpl;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public interface OBJLoader {
    static OBJLoader getInstance() {
        return OBJLoaderImpl.INSTANCE;
    }

    @Nullable
    UnbakedModel loadModel(@NotNull ResourceManager resourceManager, @NotNull ResourceLocation location) throws IOException;

    @Nullable
    UnbakedModel loadModel(@NotNull ResourceManager resourceManager, @NotNull ResourceLocation location, @NotNull ItemTransforms transforms, @NotNull OBJOption option) throws IOException;
}
