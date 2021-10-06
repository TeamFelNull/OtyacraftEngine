package red.felnull.otyacraftengine.client.renderer.texture;

import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.client.renderer.texture.NativeImage;

public class DynamicGifTexture extends DynamicTexture implements ITickable {
    private final ImageFrame[] frames;
    private final long duration;
    private int last;

    public DynamicGifTexture(long duration, ImageFrame... frames) {
        super(frames[0].getImage());
        this.duration = duration;
        this.frames = frames;
    }

    @Override
    public void tick() {
        int ct = Math.toIntExact(getFrameByTime(System.currentTimeMillis() % duration));
        if (ct != last) {
            this.dynamicTextureData = frames[ct].getImage();
            updateDynamicTexture();
            last = ct;
        }
    }

    @Override
    public void close() {
        super.close();
        if (frames != null) {
            for (ImageFrame image : frames) {
                if (image != null)
                    image.getImage().close();
            }
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

    public static class ImageFrame {
        private final NativeImage image;
        private final long delay;

        public ImageFrame(NativeImage image, long delay) {
            this.image = image;
            this.delay = delay;
        }

        public long getDelay() {
            return delay;
        }

        public NativeImage getImage() {
            return image;
        }
    }
}
