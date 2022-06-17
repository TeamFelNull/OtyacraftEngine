package dev.felnull.otyacraftengine.client.renderer.texture;

import net.minecraft.resources.ResourceLocation;

public record NativeTextureLoadResult(ResourceLocation location, Exception exception) implements TextureLoadResult {
    public NativeTextureLoadResult() {
        this(null, null);
    }

    @Override
    public boolean isSuccess() {
        return !isLoading() && !isError();
    }

    @Override
    public Exception getException() {
        return exception;
    }

    @Override
    public ResourceLocation getLocation() {
        return location;
    }

    @Override
    public boolean isLoading() {
        return location == null && exception == null;
    }

    @Override
    public boolean isError() {
        return exception != null;
    }
}
