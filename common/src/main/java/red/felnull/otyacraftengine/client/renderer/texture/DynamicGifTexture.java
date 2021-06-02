package red.felnull.otyacraftengine.client.renderer.texture;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.Tickable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DynamicGifTexture extends DynamicTexture implements Tickable {
    private static final Logger LOGGER = LogManager.getLogger(DynamicGifTexture.class);
    private final NativeImage[] images;
    private final long[] durations;
    private final long duration;
    protected int ids[];
    private boolean imageInit;
    private long lastTime;
    private long time;
    private int count;

    public DynamicGifTexture(long[] durations, NativeImage... nativeImages) {
        super(nativeImages[0]);
        this.images = nativeImages;
        this.durations = durations;

        long d = 0;
        for (long duration : durations) {
            d += duration;
        }
        this.duration = d;
/*
        ids = new int[images.length];
        for (int i = 0; i < images.length; i++) {
            ids[0] = TextureUtil.generateTextureId();
        }

        if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(() -> {
                for (NativeImage image : images) {
                    TextureUtil.prepareImage(this.getId(), image.getWidth(), image.getHeight());
                }
                imageInit = true;
                upload();
            });
        } else {
            for (NativeImage image : images) {
                TextureUtil.prepareImage(this.getId(), image.getWidth(), image.getHeight());
            }
            imageInit = true;
            upload();
        }
*/
    }

    private int t;

    @Override
    public void tick() {
        t++;
        if (t >= images.length) {
            t = 0;
        }
        setPixels(images[t]);
        upload();
    }
/*
    @Override
    public void upload() {
        if (imageInit) {
            if (this.images != null) {
                this.bind();
                for (NativeImage image : images) {
                    image.upload(0, 0, 0, false);
                }
            } else {
                LOGGER.warn("Trying to upload disposed texture {}", this.getId());
            }
        }
    }
*/


    /*   @Override
    public void tick() {

        if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(this::cycleAnimationFrames);
        } else {
            this.cycleAnimationFrames();
        }
    }

    public void cycleAnimationFrames() {
        if (count == 0)
            this.bind();
        else
            bind(count - 1);

        if (lastTime != 0)
            time += System.currentTimeMillis() - lastTime;

        lastTime = System.currentTimeMillis();

        count = 0;
        long tm = time % duration;
        long al = 0;
        for (int i = 0; i < durations.length; i++) {
            count = i;
            if (al > tm)
                break;
            al += durations[i];
        }
    }

    @Override
    public void close() {
        super.close();
        for (int i = 0; i < images.length; i++) {
            if (this.images[i] != null) {
                this.images[i].close();
                this.releaseId();
                this.images[i] = null;
            }
        }
    }

    public int getId(int num) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        if (this.ids[num] == -1) {
            this.ids[num] = TextureUtil.generateTextureId();
        }

        return 3;//this.ids[num];
    }


    public void upload(int num) {
        if (this.images[num] != null) {
            this.bind(num);
            this.images[num].upload(0, 0, 0, false);
        } else {
            LOGGER.warn("Trying to upload disposed texture {}", this.getId());
        }
    }

    @Override
    public void upload() {
           if (this.getPixels() != null) {
            bind();
            this.getPixels().upload(0, 0, 0, false);
        } else {
            LOGGER.warn("Trying to upload disposed texture {}", this.getId());
        }

    }

    @Override
    public void bind() {

    }

    public void bind(int num) {
        if (!RenderSystem.isOnRenderThreadOrInit()) {
            RenderSystem.recordRenderCall(() -> {
                GlStateManager._bindTexture(this.getId(num));
            });
        } else {
            GlStateManager._bindTexture(this.getId(num));
        }
    }*/
}
