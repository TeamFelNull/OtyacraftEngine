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
import java.util.concurrent.atomic.AtomicReference;

/**
 * テクスチャ関係
 *
 * @author MORIMORI0317
 * @since 1.0
 */
@Environment(EnvType.CLIENT)
public class IKSGTextureUtil {
    private static final Minecraft mc = Minecraft.getInstance();
    private static final Map<UUID, ResourceLocation> NATIVE_TEXTURES = new HashMap<>();
    private static final String[] imageData = {"imageLeftPosition", "imageTopPosition", "imageWidth", "imageHeight"};
    private static final Map<String, UUID> URL_TEXTURES_UUIDS = new HashMap<>();
    public static final Map<String, String> URL_TEXTURES_INDEX = new HashMap<>();
    private static final Set<String> LOADING_URLS = new HashSet<>();

    /**
     * UUIDからプレイヤースキンテクスチャ取得
     *
     * @param uuid プレイヤーUUID
     * @return プレイヤースキンテクスチャID
     * @since 2.0
     */
    public static ResourceLocation getPlayerSkinTexture(UUID uuid) {
        return getPlayerTexture(MinecraftProfileTexture.Type.SKIN, uuid);
    }

    /**
     * UUIDからプレイヤーマントテクスチャ取得
     *
     * @param uuid プレイヤーUUID
     * @return プレイヤーマントテクスチャID
     * @since 2.0
     */
    public static ResourceLocation getPlayerCapeTexture(UUID uuid) {
        return getPlayerTexture(MinecraftProfileTexture.Type.CAPE, uuid);
    }

    /**
     * UUIDからプレイヤーエリトラテクスチャ取得
     *
     * @param uuid プレイヤーUUID
     * @return プレイヤーエリトラテクスチャID
     * @since 2.0
     */
    public static ResourceLocation getPlayerElytraTexture(UUID uuid) {
        return getPlayerTexture(MinecraftProfileTexture.Type.ELYTRA, uuid);
    }

    /**
     * 名前からプレイヤースキンテクスチャ取得
     *
     * @param name プレイヤー名
     * @return プレイヤースキンテクスチャID
     */
    public static ResourceLocation getPlayerSkinTexture(String name) {
        return getPlayerTexture(MinecraftProfileTexture.Type.SKIN, name);
    }

    /**
     * 名前からプレイヤーマントテクスチャ取得
     *
     * @param name プレイヤー名
     * @return プレイヤーマントテクスチャID
     */
    public static ResourceLocation getPlayerCapeTexture(String name) {
        return getPlayerTexture(MinecraftProfileTexture.Type.CAPE, name);
    }

    /**
     * 名前からプレイヤーエリトラテクスチャ取得
     *
     * @param name プレイヤー名
     * @return プレイヤーエリトラテクスチャID
     */
    public static ResourceLocation getPlayerElytraTexture(String name) {
        return getPlayerTexture(MinecraftProfileTexture.Type.ELYTRA, name);
    }

    /**
     * 名前からプレイヤーテクスチャ取得
     *
     * @param type テクスチャタイプ
     * @param name プレイヤー名
     * @return プレイヤーテクスチャ
     */
    public static ResourceLocation getPlayerTexture(MinecraftProfileTexture.Type type, String name) {
        if (mc.player != null && mc.player.connection.getPlayerInfo(name) != null) {
            return switch (type) {
                case SKIN -> mc.player.connection.getPlayerInfo(name).getSkinLocation();
                case CAPE -> mc.player.connection.getPlayerInfo(name).getCapeLocation();
                case ELYTRA -> mc.player.connection.getPlayerInfo(name).getElytraLocation();
            };
        }
        GameProfile GP = IKSGPlayerUtil.getPlayerProfile(name);
        Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = mc.getSkinManager().getInsecureSkinInformation(GP);
        ResourceLocation faselocation = map.containsKey(type) ? mc.getSkinManager().registerTexture(map.get(type), type) : DefaultPlayerSkin.getDefaultSkin(Player.createPlayerUUID(GP));
        return faselocation;
    }

    /**
     * UUIDからプレイヤーテクスチャ取得
     *
     * @param type テクスチャタイプ
     * @param uuid プレイヤーUUID
     * @return プレイヤーテクスチャ
     * @since 2.0
     */
    public static ResourceLocation getPlayerTexture(MinecraftProfileTexture.Type type, UUID uuid) {
        if (mc.player != null && mc.player.connection.getPlayerInfo(uuid) != null) {
            return switch (type) {
                case SKIN -> mc.player.connection.getPlayerInfo(uuid).getSkinLocation();
                case CAPE -> mc.player.connection.getPlayerInfo(uuid).getCapeLocation();
                case ELYTRA -> mc.player.connection.getPlayerInfo(uuid).getElytraLocation();
            };
        }

        String name = IKSGPlayerUtil.getNameByUUIDASync(uuid);

        if (!IKSGPlayerUtil.getFakePlayerName().equals(name)) {
            return getPlayerTexture(type, name);
        }

        return type == MinecraftProfileTexture.Type.SKIN ? DefaultPlayerSkin.getDefaultSkin(uuid) : null;
    }

    public static ResourceLocation getNativeTextureAsync(UUID id, InputStream stream, ResourceLocation other) {
        if (NATIVE_TEXTURES.containsKey(id))
            return NATIVE_TEXTURES.get(id);

        ResourceLocation location = other != null ? other : MissingTextureAtlasSprite.getLocation();
        NATIVE_TEXTURES.put(id, location);

        NTextureLoadThread btlt = new NTextureLoadThread(id, stream);
        btlt.start();
        return location;
    }

    public static ResourceLocation getNativeTexture(UUID id, InputStream stream) {
        if (NATIVE_TEXTURES.containsKey(id))
            return NATIVE_TEXTURES.get(id);

        return getNativeTextureOnly(id, stream);
    }

    private static ResourceLocation getNativeTextureOnly(UUID id, InputStream stream) {

        AtomicReference<ResourceLocation> location = new AtomicReference<>(MissingTextureAtlasSprite.getLocation());

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

                        nis[i] = NativeImage.read(new ByteArrayInputStream(IKSGImageUtil.toByte(master, "png")));

                    }

                }
                mc.submit(() -> location.set(mc.getTextureManager().register("native_texture", new DynamicGifTexture(mss, nis)))).get();

                nonNomalDynamicTexture = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!nonNomalDynamicTexture) {
            try {
                NativeImage ni = NativeImage.read(new ByteArrayInputStream(data));
                mc.submit(() -> location.set(mc.getTextureManager().register("native_texture", new DynamicTexture(ni)))).get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        NATIVE_TEXTURES.put(id, location.get());
        return location.get();
    }

    public static void freeNativeTexture(UUID id) {
        if (NATIVE_TEXTURES.containsKey(id)) {
            freeTexture(NATIVE_TEXTURES.get(id));
            NATIVE_TEXTURES.remove(id);
        }
    }

    public static ResourceLocation getURLTextureAsync(String url, boolean cash, ResourceLocation other) {
        ResourceLocation location = other != null ? other : MissingTextureAtlasSprite.getLocation();

        if (LOADING_URLS.contains(url))
            return location;

        if (URL_TEXTURES_UUIDS.containsKey(url))
            return getNativeTexture(URL_TEXTURES_UUIDS.get(url), null);

        LOADING_URLS.add(url);

        UTextureLoadThread utlt = new UTextureLoadThread(url, cash);
        utlt.start();

        return location;
    }

    public static ResourceLocation getURLTexture(String url) {
        if (URL_TEXTURES_UUIDS.containsKey(url))
            return getNativeTexture(URL_TEXTURES_UUIDS.get(url), null);
        return MissingTextureAtlasSprite.getLocation();
    }

    public static ResourceLocation getURLTexture(String url, boolean cash) throws IOException, SizeOverException {
        if (URL_TEXTURES_UUIDS.containsKey(url))
            return getNativeTexture(URL_TEXTURES_UUIDS.get(url), null);
        return getURLTextureOnly(url, cash);
    }


    private static ResourceLocation getURLTextureOnly(String url, boolean cash) throws IOException, SizeOverException {


        HttpURLConnection connection = IKSGURLUtil.getConnection(new URL(url));

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
        LOADING_URLS.remove(url);
    }

    public static void freeTexture(ResourceLocation location) {
        OEClientExpectPlatform.freeTexture(location);
    }

    private static class UTextureLoadThread extends Thread {
        private final String url;
        private final boolean cash;

        private UTextureLoadThread(String url, boolean cash) {
            this.url = url;
            this.cash = cash;
            this.setName("URL Texture Loader");
        }

        @Override
        public void run() {
            try {
                getURLTexture(url, cash);
                LOADING_URLS.remove(url);
            } catch (IOException | SizeOverException e) {
                e.printStackTrace();
            }
        }
    }

    private static class NTextureLoadThread extends Thread {
        private final UUID uuid;
        private final InputStream stream;

        private NTextureLoadThread(UUID uuid, InputStream stream) {
            this.uuid = uuid;
            this.stream = stream;
            this.setName("Native Texture Loader");
        }

        @Override
        public void run() {
            getNativeTextureOnly(uuid, stream);
        }
    }
}
