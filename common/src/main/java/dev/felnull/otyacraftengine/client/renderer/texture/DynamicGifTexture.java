package dev.felnull.otyacraftengine.client.renderer.texture;

import com.madgag.gif.fmsware.GifDecoder;
import com.mojang.blaze3d.platform.NativeImage;
import dev.felnull.fnjl.util.FNImageUtil;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.Tickable;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public final class DynamicGifTexture extends DynamicTexture implements Tickable {
    private final ImageFrame[] frames;
    private final long duration;
    private int last;

    private DynamicGifTexture(long duration, ImageFrame... frames) {
        super(frames[0].image());
        this.duration = duration;
        this.frames = frames;
    }

    @Override
    public void tick() {
        int ct = Math.toIntExact(getFrameByTime(System.currentTimeMillis() % duration));
        if (ct != last) {
            this.pixels = frames[ct].image();
            upload();
            last = ct;
        }
    }

    private int getFrameByTime(long time) {
        long dr = 0;
        for (int i = 0; i < frames.length; i++) {
            if (dr <= time && dr + frames[i].delay() > time)
                return i;
            dr += frames[i].delay();
        }
        return 0;
    }

    @Override
    public void close() {
        super.close();
        if (frames != null) {
            for (ImageFrame image : frames) {
                if (image != null)
                    image.image().close();
            }
        }
    }

    @NotNull
    public static DynamicGifTexture create(@NotNull GifDecoder decoder) throws IOException {
        ImageFrame[] frames = new ImageFrame[decoder.getFrameCount()];
        long duration = 0;
        for (int i = 0; i < decoder.getFrameCount(); i++) {
            var img = decoder.getFrame(i);
            long delay = decoder.getDelay(i);
            try (var stream = FNImageUtil.toInputStream(img, "png")) {
                frames[i] = new ImageFrame(NativeImage.read(stream), delay);
            }
            duration += delay;
        }
        return new DynamicGifTexture(duration, frames);
    }

    private static record ImageFrame(NativeImage image, long delay) {
    }
}
