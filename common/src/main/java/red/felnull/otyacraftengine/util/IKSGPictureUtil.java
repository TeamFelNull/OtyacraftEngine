package red.felnull.otyacraftengine.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

public class IKSGPictureUtil {
    public static byte[] geByteImage(BufferedImage image) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            baos.flush();
            byte[] imgebyte = baos.toByteArray();
            baos.close();
            return imgebyte;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
