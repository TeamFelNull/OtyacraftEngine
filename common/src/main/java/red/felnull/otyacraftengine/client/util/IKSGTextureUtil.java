package red.felnull.otyacraftengine.client.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.blaze3d.platform.NativeImage;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.lang3.ArrayUtils;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import red.felnull.otyacraftengine.client.renderer.texture.DynamicGifTexture;
import red.felnull.otyacraftengine.util.IKSGPictureUtil;
import red.felnull.otyacraftengine.util.IKSGPlayerUtil;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

@Environment(EnvType.CLIENT)
public class IKSGTextureUtil {
    private static final Minecraft mc = Minecraft.getInstance();
    private static final Map<UUID, NativeTextureData> NATIVE_TEXTURES = new HashMap<>();
    private static final String[] imageatt = {"imageLeftPosition", "imageTopPosition", "imageWidth", "imageHeight"};

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
            return NATIVE_TEXTURES.get(id).location();
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

        NativeImage ni = null;
        ResourceLocation location = new ResourceLocation("missingno");
        NativeImage[] nis = new NativeImage[0];
        if ("gif".equalsIgnoreCase(format)) {
            try {
                ImageReader reader = ImageIO.getImageReadersByFormatName("gif").next();
                ImageInputStream ciis = ImageIO.createImageInputStream(new ByteArrayInputStream(data));
                reader.setInput(ciis, false);
                int noi = reader.getNumImages(true);
                nis = new NativeImage[noi];
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

                            for (String s : imageatt) {
                                NamedNodeMap attr = nodeItem.getAttributes();
                                Node attnode = attr.getNamedItem(s);
                                imageAttr.put(s, Integer.valueOf(attnode.getNodeValue()));
                            }

                        }

                        if (i == 0) {
                            master = new BufferedImage(imageAttr.get("imageWidth"), imageAttr.get("imageHeight"), BufferedImage.TYPE_INT_ARGB);
                        }
                        master.getGraphics().drawImage(image, imageAttr.get("imageLeftPosition"), imageAttr.get("imageTopPosition"), null);


                        nis[i] = NativeImage.read(new ByteArrayInputStream(IKSGPictureUtil.geByteImage(master)));

                    }


                }

                location = Minecraft.getInstance().getTextureManager().register("native_texture", new DynamicGifTexture(mss, nis));
                nonNomalDynamicTexture = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        if (!nonNomalDynamicTexture) {
            System.out.println("test");
            try {
                ni = NativeImage.read(new ByteArrayInputStream(data));
                location = Minecraft.getInstance().getTextureManager().register("native_texture", new DynamicTexture(ni));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        nis = ArrayUtils.add(nis, ni);
        NATIVE_TEXTURES.put(id, new NativeTextureData(location, nis));
        return location;
    }

    public static void freeNativeTexture(UUID id) {
        if (NATIVE_TEXTURES.containsKey(id)) {
            NativeImage[] nativeImage = NATIVE_TEXTURES.get(id).image();
            if (nativeImage != null) {
                for (NativeImage image : nativeImage) {
                    if (image != null)
                        image.close();
                }
            }
            NATIVE_TEXTURES.remove(id);
        }
    }

    public static ResourceLocation getWorldShareTexture(UUID id) {

        return null;
    }


    private record NativeTextureData(ResourceLocation location, NativeImage... image) {

    }
}
