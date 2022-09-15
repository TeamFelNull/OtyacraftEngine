package dev.felnull.otyacraftengine.client.renderer.texture.impl;

import dev.felnull.otyacraftengine.client.renderer.texture.TextureLoadProgress;
import dev.felnull.otyacraftengine.client.renderer.texture.TextureLoadResult;
import net.minecraft.resources.ResourceLocation;

import java.util.UUID;

public class URLTextureLoadResult implements TextureLoadResult {
    private final Exception exception;
    private final boolean needReload;
    private final long loadedTime;
    private final NativeTextureLoadResult loadResult;
    private final UUID uuid;
    private TextureLoadProgress progress = new TextureLoadProgressImpl();

    public URLTextureLoadResult(Exception exception, boolean needReload, long loadedTime, NativeTextureLoadResult loadResult, UUID uuid) {
        this.exception = exception;
        this.needReload = needReload;
        this.loadedTime = loadedTime;
        this.loadResult = loadResult;
        this.uuid = uuid;
    }

    public URLTextureLoadResult(Exception exception, boolean needReload, NativeTextureLoadResult loadResult, UUID uuid) {
        this(exception, needReload, System.currentTimeMillis(), loadResult, uuid);
    }

    @Override
    public ResourceLocation getLocation() {
        if (loadResult == null)
            return null;
        return loadResult.getLocation();
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
        if (exception != null)
            return exception;
        if (loadResult != null)
            return loadResult.getException();
        return null;
    }

    @Override
    public TextureLoadProgress getProgress() {
        return progress;
    }

    public long getLoadedTime() {
        return loadedTime;
    }

    public UUID getUUID() {
        return uuid;
    }

    public boolean isNeedReload() {
        return needReload;
    }

    public NativeTextureLoadResult getNativeResult() {
        return loadResult;
    }

    public void setProgress(TextureLoadProgress progress) {
        this.progress = progress;
    }
}
