package dev.felnull.otyacraftengine.util;

import com.madgag.gif.fmsware.AnimatedGifEncoder;
import com.madgag.gif.fmsware.GifDecoder;
import dev.felnull.fnjl.math.FNVec2i;
import dev.felnull.fnjl.util.FNImageUtil;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class OEImageUtil {
    public static boolean isImage(byte[] data) {
        return isGif(data) || FNImageUtil.isImage(data);
    }

    public static byte[] reductionSize(byte[] data, long size) throws IOException {
        long lastSize = data.length;
        if (lastSize <= size) return data;

        GifDecoder decoder = new GifDecoder();
        if (decoder.read(new ByteArrayInputStream(data)) != 0)
            return FNImageUtil.toByteArray(FNImageUtil.reductionSize(ImageIO.read(new ByteArrayInputStream(data)), size), "png");

        float scale = (float) size / lastSize;
        var out = new ByteArrayOutputStream();
        AnimatedGifEncoder encoder = new AnimatedGifEncoder();
        encoder.start(out);
        encoder.setRepeat(0);
        FNVec2i siz = null;
        for (int i = 0; i < decoder.getFrameCount(); i++) {
            var fb = decoder.getFrame(i);
            var le = FNImageUtil.toByteArray(fb, "png");
            var img = FNImageUtil.reductionSize(fb, (long) ((float) le.length * scale));
            if (siz == null) {
                siz = new FNVec2i(img.getWidth(), img.getHeight());
            } else {
                if (siz.getX() > img.getWidth() && siz.getY() > img.getHeight())
                    siz = new FNVec2i(img.getWidth(), img.getHeight());
            }
        }
        if (siz != null) {
            for (int i = 0; i < decoder.getFrameCount(); i++) {
                encoder.setDelay(decoder.getDelay(i));
                encoder.addFrame(FNImageUtil.resize(decoder.getFrame(i), siz.getX(), siz.getY()));
            }
        }

        encoder.finish();
        return out.toByteArray();
    }

    public static boolean isGif(byte[] data) {
        GifDecoder decoder = new GifDecoder();
        return decoder.read(new ByteArrayInputStream(data)) == 0;
    }
}
