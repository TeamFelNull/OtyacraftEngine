package dev.felnull.otyacraftengine.client.renderer.texture;

import dev.felnull.otyacraftengine.client.util.OETextureUtils;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record TextureLoadResult(ResourceLocation location, Exception exception) {
    public TextureLoadResult() {
        this(null, null);
    }

    public boolean isLoading() {
        return location == null && exception == null;
    }

    public boolean isNoError() {
        return exception == null;
    }

    public boolean isSuccess() {
        return !isLoading() && isNoError();
    }

    @NotNull
    public ResourceLocation of(@NotNull ResourceLocation loading, @NotNull ResourceLocation error) {
        if (isLoading())
            return loading;
        if (exception != null)
            return error;
        return location;
    }

    @NotNull
    public ResourceLocation of(@NotNull ResourceLocation error) {
        return of(OETextureUtils.getLoadingIcon(), error);
    }

    @NotNull
    public ResourceLocation of() {
        return of(OETextureUtils.getLoadingIcon(), OETextureUtils.getErrorIcon());
    }
}
