package dev.felnull.otyacraftengine.client.renderer.texture;


import com.mojang.blaze3d.platform.NativeImage;
import dev.felnull.otyacraftengine.impl.client.OEClientExpectPlatform;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.Tickable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DynamicGifTexture extends DynamicTexture implements Tickable {
    private static final Logger LOGGER = LogManager.getLogger(DynamicGifTexture.class);
    private final NativeImage[] images;
    private final long[] durations;
    private final long duration;
    private int count;
    private int lastCount;

    public DynamicGifTexture(long[] durations, NativeImage... nativeImages) {
        super(nativeImages[0]);
        this.images = nativeImages;
        this.durations = durations;

        long d = 0;
        for (long duration : durations) {
            d += duration;
        }
        this.duration = d;
    }

    @Override
    public void tick() {
        count = 0;
        long tm = System.currentTimeMillis() % duration;
        long al = 0;
        for (int i = 0; i < durations.length; i++) {
            count = i;
            if (al > tm)
                break;
            al += durations[i];
        }

        if (lastCount != count) {
            OEClientExpectPlatform.setNonClosePixels(this, images[count]);
            upload();
            lastCount = count;
        }
    }

    @Override
    public void close() {
        super.close();
        if (images != null) {
            for (NativeImage image : images) {
                if (image != null)
                    image.close();
            }
        }
    }
}