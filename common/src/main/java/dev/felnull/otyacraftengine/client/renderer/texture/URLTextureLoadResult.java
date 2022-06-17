package dev.felnull.otyacraftengine.client.renderer.texture;

import net.minecraft.resources.ResourceLocation;

import java.util.UUID;

public record URLTextureLoadResult(Exception exception, boolean needReload, long loadedTime,
                                   NativeTextureLoadResult loadResult, UUID uuid) implements TextureLoadResult {
    public URLTextureLoadResult(Exception exception, boolean needReload, NativeTextureLoadResult loadResult, UUID uuid) {
        this(exception, needReload, System.currentTimeMillis(), loadResult, uuid);
    }

    @Override
    public ResourceLocation getLocation() {
        if (loadResult == null)
            return null;
        return loadResult.location();
    }

    @Override
    public boolean isLoading() {
        return getLocation() == null && exception == null && loadedTime == -1;
    }

    @Override
    public boolean isError() {
        return exception != null;
    }

    @Override
    public boolean isSuccess() {
        return !isLoading() && !isError();
    }

    @Override
    public Exception getException() {
        return exception;
    }
}
