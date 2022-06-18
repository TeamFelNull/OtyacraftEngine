package dev.felnull.otyacraftengine.client.renderer.texture;

import net.minecraft.resources.ResourceLocation;

public class NativeTextureLoadResult implements TextureLoadResult {
    private final ResourceLocation location;
    private final Exception exception;
    private TextureLoadProgress progress;

    public NativeTextureLoadResult(ResourceLocation location, Exception exception, TextureLoadProgress progress) {
        this.location = location;
        this.exception = exception;
        this.progress = progress;
    }

    public NativeTextureLoadResult(ResourceLocation location, Exception exception) {
        this(location, exception, null);
    }

    public NativeTextureLoadResult() {
        this(null, null, new TextureLoadProgressImpl());
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
    public TextureLoadProgress getProgress() {
        return progress;
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

    protected void setProgress(TextureLoadProgress progress) {
        this.progress = progress;
    }
}
