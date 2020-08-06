package red.felnull.otyacraftengine.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class IKSGPictuerUtil {
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

    public static BufferedImage geBfftImage(byte[] bytes) {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        try {
            BufferedImage bi = ImageIO.read(bis);
            return bi;
        } catch (IOException e) {
            return null;
        }
    }

    public static byte[] geByteImage(BufferedImage image) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            baos.flush();
            byte[] imgebyte = baos.toByteArray();
            baos.close();
            return imgebyte;
        } catch (Exception e) {
        }
        return null;
    }

}
