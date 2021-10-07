package red.felnull.otyacraftengine.util;

import com.madgag.gif.fmsware.AnimatedGifEncoder;
import com.madgag.gif.fmsware.GifDecoder;
import dev.felnull.fnjl.util.FNImageUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class IKSGPictuerUtil {
    @Deprecated
    public static BufferedImage getBffImage(Path path) {
        File picfile = path.toFile();
        if (picfile.exists() && picfile != null) {
            try {
                BufferedImage bi = ImageIO.read(picfile);
                return bi;
            } catch (IOException e) {
            }
        }
        return null;
    }

    @Deprecated
    public static BufferedImage geBfftImage(byte[] bytes) {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        try {
            BufferedImage bi = ImageIO.read(bis);
            return bi;
        } catch (IOException e) {
            return null;
        }
    }

    @Deprecated
    public static byte[] geByteImage(BufferedImage image) {
        try {
            return FNImageUtil.toByteArray(image, "png");
        } catch (Exception ignored) {
        }
        return null;
    }

    @Deprecated
    public static BufferedImage resize(BufferedImage image, int width, int height) {
        return FNImageUtil.resize(image, width, height);
    }

    public static byte[] resize(byte[] image, int width, int height) throws IOException {
        GifDecoder decoder = new GifDecoder();
        if (decoder.read(new ByteArrayInputStream(image)) == GifDecoder.STATUS_OK) {
            AnimatedGifEncoder encoder = new AnimatedGifEncoder();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            encoder.start(baos);
            for (int i = 0; i < decoder.getFrameCount(); i++) {
                encoder.setDelay(decoder.getDelay(i));
                encoder.addFrame(FNImageUtil.resize(decoder.getFrame(i), width, height));
            }
            encoder.finish();
            byte[] data = baos.toByteArray();
            baos.close();
            return data;
        } else {
            return FNImageUtil.toByteArray(FNImageUtil.resize(ImageIO.read(new ByteArrayInputStream(image)), width, height), "png");
        }
    }

    public static int getWidth(byte[] image) throws IOException {
        GifDecoder decoder = new GifDecoder();
        if (decoder.read(new ByteArrayInputStream(image)) == GifDecoder.STATUS_OK) {
            return (int) decoder.getFrameSize().getWidth();
        } else {
            return ImageIO.read(new ByteArrayInputStream(image)).getWidth();
        }
    }

    public static int getHeight(byte[] image) throws IOException {
        GifDecoder decoder = new GifDecoder();
        if (decoder.read(new ByteArrayInputStream(image)) == GifDecoder.STATUS_OK) {
            return (int) decoder.getFrameSize().getHeight();
        } else {
            return ImageIO.read(new ByteArrayInputStream(image)).getHeight();
        }
    }
}
