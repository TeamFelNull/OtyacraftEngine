package dev.felnull.otyacraftengine.client.renderer.texture;

import dev.felnull.otyacraftengine.client.util.OETextureUtils;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public interface TextureLoadResult {
    ResourceLocation getLocation();

    boolean isLoading();

    boolean isError();

    boolean isSuccess();

    Exception getException();

    @NotNull
    default ResourceLocation of(@NotNull ResourceLocation loading, @NotNull ResourceLocation error) {
        if (isLoading()) return loading;
        if (isError()) return error;
        return getLocation();
    }

    @NotNull
    default ResourceLocation of(@NotNull ResourceLocation error) {
        return of(OETextureUtils.getLoadingIcon(), error);
    }

    @NotNull
    default ResourceLocation of() {
        return of(OETextureUtils.getLoadingIcon(), OETextureUtils.getErrorIcon());
    }

    TextureLoadProgress getProgress();
}
