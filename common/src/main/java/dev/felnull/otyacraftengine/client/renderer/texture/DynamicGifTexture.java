package dev.felnull.otyacraftengine.client.renderer.texture;


import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.Tickable;

public class DynamicGifTexture extends DynamicTexture implements Tickable {
    private final ImageFrame[] frames;
    private final long duration;
    private int last;

    public DynamicGifTexture(long duration, ImageFrame... frames) {
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
            if (dr <= time && dr + frames[i].delay > time)
                return i;
            dr += frames[i].delay;
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

    public static record ImageFrame(NativeImage image, long delay) {
    }
}