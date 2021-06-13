package red.felnull.otyacraftengine.client.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.blaze3d.platform.NativeImage;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import red.felnull.otyacraftengine.client.impl.OEClientExpectPlatform;
import red.felnull.otyacraftengine.client.renderer.texture.DynamicGifTexture;
import red.felnull.otyacraftengine.throwable.SizeOverException;
import red.felnull.otyacraftengine.util.*;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Environment(EnvType.CLIENT)
public class IKSGTextureUtil {
    private static final Minecraft mc = Minecraft.getInstance();
    private static final Map<UUID, ResourceLocation> NATIVE_TEXTURES = new HashMap<>();
    private static final String[] imageData = {"imageLeftPosition", "imageTopPosition", "imageWidth", "imageHeight"};
    private static final Map<String, UUID> URL_TEXTURES_UUIDS = new HashMap<>();
    public static final Map<String, String> URL_TEXTURES_INDEX = new HashMap<>();

    public static ResourceLocation getPlayerSkinTexture(String name) {
        return getPlayerTexture(MinecraftProfileTexture.Type.SKIN, name);
    }

    public static ResourceLocation getPlayerCapeTexture(String name) {
        return getPlayerTexture(MinecraftProfileTexture.Type.CAPE, name);
    }

    public static ResourceLocation getPlayerElytraTexture(String name) {
        return getPlayerTexture(MinecraftProfileTexture.Type.ELYTRA, name);
    }

    public static ResourceLocation getPlayerTexture(MinecraftProfileTexture.Type type, String name) {
        if (mc.player != null && name.equals(mc.player.getGameProfile().getName())) {
            return mc.player.getSkinTextureLocation();
        }
        GameProfile GP = IKSGPlayerUtil.getPlayerProfile(name);
        Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = mc.getSkinManager().getInsecureSkinInformation(GP);
        ResourceLocation faselocation = map.containsKey(type) ? mc.getSkinManager().registerTexture(map.get(type), type) : DefaultPlayerSkin.getDefaultSkin(Player.createPlayerUUID(GP));
        return faselocation;
    }


    public static ResourceLocation getNativeTexture(UUID id, InputStream stream) {
        if (NATIVE_TEXTURES.containsKey(id))
            return NATIVE_TEXTURES.get(id);

        ResourceLocation location = new ResourceLocation("missingno");
        NATIVE_TEXTURES.put(id, location);

        byte[] data = null;
        String format = null;
        try {
            data = stream.readAllBytes();
            ImageInputStream istream = ImageIO.createImageInputStream(new ByteArrayInputStream(data));
            Iterator<ImageReader> iter = ImageIO.getImageReaders(istream);
            ImageReader reader = iter.next();
            format = reader.getFormatName();
        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean nonNomalDynamicTexture = false;

        if ("gif".equalsIgnoreCase(format)) {
            try {
                ImageReader reader = ImageIO.getImageReadersByFormatName("gif").next();
                ImageInputStream ciis = ImageIO.createImageInputStream(new ByteArrayInputStream(data));
                reader.setInput(ciis, false);
                int noi = reader.getNumImages(true);
                NativeImage[] nis = new NativeImage[noi];
                BufferedImage master = null;
                long[] mss = new long[noi];
                for (int i = 0; i < noi; i++) {
                    BufferedImage image = reader.read(i);
                    IIOMetadata metadata = reader.getImageMetadata(i);
                    Node tree = metadata.getAsTree("javax_imageio_gif_image_1.0");
                    NodeList children = tree.getChildNodes();

                    Map<String, Integer> imageAttr = null;
                    for (int j = 0; j < children.getLength(); j++) {
                        Node nodeItem = children.item(j);

                        if (nodeItem.getNodeName().equals("GraphicControlExtension")) {
                            NamedNodeMap attr = nodeItem.getAttributes();
                            mss[i] = Long.parseLong(attr.getNamedItem("delayTime").getNodeValue() + "0");
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

                        nis[i] = NativeImage.read(new ByteArrayInputStream(IKSGImageUtil.toByte(master,"png")));

                    }

                }

                location = Minecraft.getInstance().getTextureManager().register("native_texture", new DynamicGifTexture(mss, nis));
                nonNomalDynamicTexture = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!nonNomalDynamicTexture) {
            try {
                NativeImage ni = NativeImage.read(new ByteArrayInputStream(data));
                location = Minecraft.getInstance().getTextureManager().register("native_texture", new DynamicTexture(ni));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        NATIVE_TEXTURES.put(id, location);
        return location;
    }

    public static void freeNativeTexture(UUID id) {
        if (NATIVE_TEXTURES.containsKey(id)) {
            freeTexture(NATIVE_TEXTURES.get(id));
            NATIVE_TEXTURES.remove(id);
        }
    }

    public static ResourceLocation getURLTexture(String url) {
        if (URL_TEXTURES_UUIDS.containsKey(url))
            return getNativeTexture(URL_TEXTURES_UUIDS.get(url), null);

        return MissingTextureAtlasSprite.getLocation();
    }

    public static ResourceLocation getURLTexture(String url, boolean cash) throws IOException, SizeOverException {
        if (URL_TEXTURES_UUIDS.containsKey(url))
            return getNativeTexture(URL_TEXTURES_UUIDS.get(url), null);

        HttpURLConnection connection = IKSGURLUtil.getAgentURLConnection(new URL(url));

        long length = connection.getContentLengthLong();
        long maxL = 1024L * 1024L;

        if (length > maxL)
            throw new SizeOverException(length, maxL);

        InputStream stream = null;

        if (cash) {
            String b64url = Base64.getEncoder().encodeToString(url.getBytes(StandardCharsets.UTF_8));
            boolean nonFlag = false;
            if (URL_TEXTURES_INDEX.containsKey(b64url)) {
                String cid = URL_TEXTURES_INDEX.get(b64url);
                File cf = IKSGPathUtil.getCashPath().resolve(cid).toFile();
                if (cf.exists()) {
                    stream = IKSGDataUtil.unzipGz(new FileInputStream(cf));
                } else {
                    nonFlag = true;
                }
            } else {
                nonFlag = true;
            }

            if (nonFlag) {
                byte[] data = connection.getInputStream().readAllBytes();
                ImageInputStream istream = ImageIO.createImageInputStream(new ByteArrayInputStream(data));
                ImageIO.getImageReaders(istream).next();
                String cid = UUID.randomUUID().toString();
                Path cp = IKSGPathUtil.getCashPath();
                cp.toFile().mkdirs();
                byte[] gzdata = IKSGDataUtil.zipGz(new ByteArrayInputStream(data)).readAllBytes();
                Files.write(cp.resolve(cid), gzdata);
                URL_TEXTURES_INDEX.put(b64url, cid);
                stream = new ByteArrayInputStream(data);
            }
        } else {
            stream = connection.getInputStream();
        }

        UUID id = UUID.randomUUID();
        URL_TEXTURES_UUIDS.put(url, id);
        return getNativeTexture(id, stream);
    }

    public static void freeURLTexture(String url) {
        freeNativeTexture(URL_TEXTURES_UUIDS.get(url));
        URL_TEXTURES_UUIDS.remove(url);
    }

    public static ResourceLocation getWorldShareTexture(UUID id) {

        return null;
    }

    public static void freeTexture(ResourceLocation location) {
        OEClientExpectPlatform.freeTexture(location);
    }


}
