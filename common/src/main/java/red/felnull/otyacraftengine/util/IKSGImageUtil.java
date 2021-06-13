package red.felnull.otyacraftengine.util;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class IKSGImageUtil {
    private static final String[] imageData = {"imageLeftPosition", "imageTopPosition", "imageWidth", "imageHeight"};

    public static byte[] toByte(BufferedImage image, String formatName) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, formatName, baos);
        baos.flush();
        byte[] imgebyte = baos.toByteArray();
        baos.close();
        return imgebyte;
    }

    public static BufferedImage resize(BufferedImage image, int width, int height) {
        BufferedImage outImage = new BufferedImage(width, height, image.getType());
        outImage.createGraphics().drawImage(image.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING), 0, 0, width, height, null);
        return outImage;
    }

    public static String getFormatName(byte[] data) throws IOException {
        ImageInputStream istream = ImageIO.createImageInputStream(new ByteArrayInputStream(data));
        Iterator<ImageReader> iter = ImageIO.getImageReaders(istream);
        ImageReader reader = iter.next();
        return reader.getFormatName();
    }

    public static String getFormatName(InputStream stream) throws IOException {
        ImageInputStream istream = ImageIO.createImageInputStream(stream);
        Iterator<ImageReader> iter = ImageIO.getImageReaders(istream);
        ImageReader reader = iter.next();
        return reader.getFormatName();
    }

    public static BufferedImage[] gifDivide(byte[] data) throws IOException {
        if (!"gif".equalsIgnoreCase(getFormatName(data))) {
            throw new IOException("Not gif");
        }
        ImageReader reader = ImageIO.getImageReadersByFormatName("gif").next();
        ImageInputStream ciis = ImageIO.createImageInputStream(new ByteArrayInputStream(data));
        reader.setInput(ciis, false);
        int noi = reader.getNumImages(true);
        BufferedImage[] images = new BufferedImage[noi];
        BufferedImage master = null;
        for (int i = 0; i < noi; i++) {
            BufferedImage image = reader.read(i);
            IIOMetadata metadata = reader.getImageMetadata(i);
            Node tree = metadata.getAsTree("javax_imageio_gif_image_1.0");
            NodeList children = tree.getChildNodes();

            Map<String, Integer> imageAttr = null;
            for (int j = 0; j < children.getLength(); j++) {
                Node nodeItem = children.item(j);
                if (nodeItem.getNodeName().equals("ImageDescriptor")) {
                    imageAttr = new HashMap<>();

                    for (String s : imageData) {
                        NamedNodeMap attr = nodeItem.getAttributes();
                        Node attnode = attr.getNamedItem(s);
                        imageAttr.put(s, Integer.valueOf(attnode.getNodeValue()));
                    }

                }
                if (i == 0) {
                    master = new BufferedImage(imageAttr.get("imageWidth"), imageAttr.get("imageHeight"), BufferedImage.TYPE_INT_ARGB);
                }
                master.getGraphics().drawImage(image, imageAttr.get("imageLeftPosition"), imageAttr.get("imageTopPosition"), null);

                images[i] = resize(master, master.getWidth(), master.getHeight());
            }

        }
        return images;
    }

    public static byte[] reframeGif(byte[] data, int frameCont) throws IOException {
        if (!"gif".equalsIgnoreCase(getFormatName(data))) {
            throw new IOException("Not gif");
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Iterator<ImageWriter> it = ImageIO.getImageWritersByFormatName("gif");
        ImageWriter writer = it.hasNext() ? it.next() : null;
        ImageOutputStream stream = ImageIO.createImageOutputStream(baos);
        writer.setOutput(stream);
        writer.prepareWriteSequence(null);
        BufferedImage mog = ImageIO.read(new ByteArrayInputStream(data));
        BufferedImage image = new BufferedImage(mog.getWidth(), mog.getHeight(), BufferedImage.TYPE_INT_ARGB);

        ImageWriteParam iwp = writer.getDefaultWriteParam();
        IIOMetadata metadata = writer.getDefaultImageMetadata(new ImageTypeSpecifier(image), iwp);
        String metaFormat = metadata.getNativeMetadataFormatName();
        Node root = metadata.getAsTree(metaFormat);
        IIOMetadataNode aes = new IIOMetadataNode("ApplicationExtensions");
        IIOMetadataNode ae = new IIOMetadataNode("ApplicationExtension");
        ae.setAttribute("applicationID", "NETSCAPE");
        ae.setAttribute("authenticationCode", "2.0");

        ImageAndDelayTime[] adt = gifDiavideDelayTime(data);

        byte[] uo = {0x1, 0x0, 0x0};
        ae.setUserObject(uo);
        aes.appendChild(ae);
        root.appendChild(aes);
        metadata.setFromTree(metaFormat, root);
        int motFc = adt.length;

        if (motFc <= frameCont)
            return data;

        int frampar = (int) Math.floor((double) motFc / frameCont);

        ImageAndDelayTime[] cabt = new ImageAndDelayTime[frameCont];
        int c = 0;
        int cc = 0;
        for (ImageAndDelayTime imageAndDelayTime : adt) {
            if (c % frampar == 0) {
                long dt = 0;
                for (int i = c; i < Math.min(motFc, c + frampar); i++) {
                    dt += adt[i].delayTime();
                }
                cabt[cc] = new ImageAndDelayTime(imageAndDelayTime.image(), dt / 10);
                cc++;
                if (cc >= frameCont)
                    break;
            }
            c++;
        }


        for (ImageAndDelayTime imageAndDelayTime : cabt) {
            if (metadata == null) {
                metadata = writer.getDefaultImageMetadata(new ImageTypeSpecifier(image), iwp);
            }
            Node root2 = metadata.getAsTree(metaFormat);
            IIOMetadataNode gce2 = new IIOMetadataNode("GraphicControlExtension");
            gce2.setAttribute("disposalMethod", "none");
            gce2.setAttribute("userInputFlag", "FALSE");
            gce2.setAttribute("transparentColorFlag", "FALSE");
            gce2.setAttribute("transparentColorIndex", "0");
            gce2.setAttribute("delayTime", String.valueOf(imageAndDelayTime.delayTime()));
            root2.appendChild(gce2);
            metadata.setFromTree(metaFormat, root2);
            writer.writeToSequence(new IIOImage(resize(imageAndDelayTime.image(), mog.getWidth(), mog.getHeight()), null, metadata), null);
            metadata = null;
        }

        writer.endWriteSequence();
        stream.close();

        baos.flush();
        byte[] imgebyte = baos.toByteArray();
        baos.close();
        return imgebyte;
    }

    public static ImageAndDelayTime[] gifDiavideDelayTime(byte[] data) throws IOException {
        if (!"gif".equalsIgnoreCase(getFormatName(data))) {
            throw new IOException("Not gif");
        }
        ImageReader reader = ImageIO.getImageReadersByFormatName("gif").next();
        ImageInputStream ciis = ImageIO.createImageInputStream(new ByteArrayInputStream(data));
        reader.setInput(ciis, false);
        int noi = reader.getNumImages(true);
        ImageAndDelayTime[] images = new ImageAndDelayTime[noi];
        BufferedImage master = null;
        for (int i = 0; i < noi; i++) {
            BufferedImage image = reader.read(i);
            IIOMetadata metadata = reader.getImageMetadata(i);
            Node tree = metadata.getAsTree("javax_imageio_gif_image_1.0");
            NodeList children = tree.getChildNodes();

            Map<String, Integer> imageAttr = null;
            for (int j = 0; j < children.getLength(); j++) {
                Node nodeItem = children.item(j);
                long dt = 0;
                if (nodeItem.getNodeName().equals("GraphicControlExtension")) {
                    NamedNodeMap attr = nodeItem.getAttributes();
                    dt = Long.parseLong(attr.getNamedItem("delayTime").getNodeValue()) * 10;
                }
                if (nodeItem.getNodeName().equals("ImageDescriptor")) {
                    imageAttr = new HashMap<>();

                    for (String s : imageData) {
                        NamedNodeMap attr = nodeItem.getAttributes();
                        Node attnode = attr.getNamedItem(s);
                        imageAttr.put(s, Integer.valueOf(attnode.getNodeValue()));
                    }

                }

                if (i == 0) {
                    master = new BufferedImage(imageAttr.get("imageWidth"), imageAttr.get("imageHeight"), BufferedImage.TYPE_INT_ARGB);
                }
                master.getGraphics().drawImage(image, imageAttr.get("imageLeftPosition"), imageAttr.get("imageTopPosition"), null);

                images[i] = new ImageAndDelayTime(resize(master, master.getWidth(), master.getHeight()), dt);
            }

        }
        return images;
    }

    public static byte[] resizeGif(byte[] data, int width, int height) throws IOException {
        if (!"gif".equalsIgnoreCase(getFormatName(data))) {
            throw new IOException("Not gif");
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Iterator<ImageWriter> it = ImageIO.getImageWritersByFormatName("gif");
        ImageWriter writer = it.hasNext() ? it.next() : null;
        ImageOutputStream stream = ImageIO.createImageOutputStream(baos);
        writer.setOutput(stream);
        writer.prepareWriteSequence(null);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        ImageWriteParam iwp = writer.getDefaultWriteParam();
        IIOMetadata metadata = writer.getDefaultImageMetadata(new ImageTypeSpecifier(image), iwp);
        String metaFormat = metadata.getNativeMetadataFormatName();
        Node root = metadata.getAsTree(metaFormat);
        IIOMetadataNode aes = new IIOMetadataNode("ApplicationExtensions");
        IIOMetadataNode ae = new IIOMetadataNode("ApplicationExtension");
        ae.setAttribute("applicationID", "NETSCAPE");
        ae.setAttribute("authenticationCode", "2.0");

        ImageAndDelayTime[] adt = gifDiavideDelayTime(data);

        byte[] uo = {0x1, 0x0, 0x0};
        ae.setUserObject(uo);
        aes.appendChild(ae);
        root.appendChild(aes);
        metadata.setFromTree(metaFormat, root);

        for (ImageAndDelayTime imageAndDelayTime : adt) {
            if (metadata == null) {
                metadata = writer.getDefaultImageMetadata(new ImageTypeSpecifier(image), iwp);
            }
            Node root2 = metadata.getAsTree(metaFormat);
            IIOMetadataNode gce2 = new IIOMetadataNode("GraphicControlExtension");
            gce2.setAttribute("disposalMethod", "none");
            gce2.setAttribute("userInputFlag", "FALSE");
            gce2.setAttribute("transparentColorFlag", "FALSE");
            gce2.setAttribute("transparentColorIndex", "0");
            gce2.setAttribute("delayTime", String.valueOf(imageAndDelayTime.delayTime() / 10));
            root2.appendChild(gce2);
            metadata.setFromTree(metaFormat, root2);
            writer.writeToSequence(new IIOImage(resize(imageAndDelayTime.image(), width, height), null, metadata), null);
            metadata = null;
        }

        writer.endWriteSequence();
        stream.close();

        baos.flush();
        byte[] imgebyte = baos.toByteArray();
        baos.close();
        return imgebyte;
    }

    public static record ImageAndDelayTime(BufferedImage image, long delayTime) {
    }
}
