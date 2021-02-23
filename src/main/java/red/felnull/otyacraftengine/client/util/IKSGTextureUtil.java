package red.felnull.otyacraftengine.client.util;

import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.client.data.ReceiveTextureLoder;
import red.felnull.otyacraftengine.client.data.URLImageTextureLoder;
import red.felnull.otyacraftengine.util.IKSGFileLoadUtil;
import red.felnull.otyacraftengine.util.IKSGPlayerUtil;

import java.io.ByteArrayInputStream;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@OnlyIn(Dist.CLIENT)
public class IKSGTextureUtil {
    private static final ResourceLocation LOADING_1 = new ResourceLocation(OtyacraftEngine.MODID, "textures/gui/loading_icon/loading_1.png");
    private static final ResourceLocation LOADING_2 = new ResourceLocation(OtyacraftEngine.MODID, "textures/gui/loading_icon/loading_2.png");
    private static final ResourceLocation LOADING_3 = new ResourceLocation(OtyacraftEngine.MODID, "textures/gui/loading_icon/loading_3.png");
    private static final ResourceLocation LOADING_4 = new ResourceLocation(OtyacraftEngine.MODID, "textures/gui/loading_icon/loading_4.png");
    public static final ResourceLocation TEXTUER_LOADING = new ResourceLocation(OtyacraftEngine.MODID, "textures/gui/textuer_loading.png");
    public static final ResourceLocation TEXTUER_NOTFINED = new ResourceLocation(OtyacraftEngine.MODID, "textures/gui/textuer_not_find.png");

    public static int loadingPaatune;
    private static final Minecraft mc = Minecraft.getInstance();
    private static final Map<byte[], ResourceLocation> PICTUER_BYTE_LOCATION = Maps.newHashMap();

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
        if (mc.player != null && name.equals(IKSGPlayerUtil.getUserName(mc.player))) {
            return mc.player.getLocationSkin();
        }
        ResourceLocation faselocation;
        GameProfile GP = IKSGPlayerUtil.getPlayerProfile(name);
        Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = mc.getSkinManager().loadSkinFromCache(GP);
        faselocation = map.containsKey(type) ? mc.getSkinManager().loadSkin(map.get(type), type) : DefaultPlayerSkin.getDefaultSkin(PlayerEntity.getUUID(GP));
        return faselocation;
    }

    public static ResourceLocation getPlayerSkinTextureByUUID(String uuid) {
        return getPlayerTextureByUUID(MinecraftProfileTexture.Type.SKIN, uuid);
    }

    public static ResourceLocation getPlayerCapeTextureByUUID(String uuid) {
        return getPlayerTextureByUUID(MinecraftProfileTexture.Type.CAPE, uuid);
    }

    public static ResourceLocation getPlayerElytraTextureByUUID(String uuid) {
        return getPlayerTextureByUUID(MinecraftProfileTexture.Type.ELYTRA, uuid);
    }

    public static ResourceLocation getPlayerTextureByUUID(MinecraftProfileTexture.Type type, String uuid) {
        if (mc.player != null && uuid.equals(IKSGPlayerUtil.getUUID(mc.player))) {
            return mc.player.getLocationSkin();
        }
        ResourceLocation faselocation;
        GameProfile GP = IKSGPlayerUtil.getPlayerProfileByUUID(uuid);
        Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = mc.getSkinManager().loadSkinFromCache(GP);
        faselocation = map.containsKey(type) ? mc.getSkinManager().loadSkin(map.get(type), type) : DefaultPlayerSkin.getDefaultSkin(PlayerEntity.getUUID(GP));
        return faselocation;
    }


    public static ResourceLocation getPictureImageTexture(byte[] data) {
        if (PICTUER_BYTE_LOCATION.containsKey(data)) {
            return PICTUER_BYTE_LOCATION.get(data);
        }
        ResourceLocation imagelocation = new ResourceLocation(OtyacraftEngine.MODID, "pictuer/" + UUID.randomUUID().toString());
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            NativeImage NI = NativeImage.read(bis);
            Minecraft.getInstance().textureManager.loadTexture(imagelocation, new DynamicTexture(NI));
            PICTUER_BYTE_LOCATION.put(data, imagelocation);
            return imagelocation;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static ResourceLocation getLoadingIconTextuer() {
        if (loadingPaatune == 0) {
            return LOADING_1;
        } else if (loadingPaatune == 1) {
            return LOADING_2;
        } else if (loadingPaatune == 2) {
            return LOADING_3;
        } else if (loadingPaatune == 3) {
            return LOADING_4;
        }
        return LOADING_1;
    }


    public static ResourceLocation getReceiveTexture(ResourceLocation location, String name) {
        String WORLDNAME_AND_PATH = IKSGClientUtil.getCurrentWorldUUID() + ":" + location.toString() + ":" + name;

        if (ReceiveTextureLoder.instance().PICTUER_RECEIVE_LOCATION.containsKey(WORLDNAME_AND_PATH)) {
            ReceiveTextureLoder.instance().updateLastTextuerTime(location, name);
            return ReceiveTextureLoder.instance().PICTUER_RECEIVE_LOCATION.get(WORLDNAME_AND_PATH);
        }

        String filename = ReceiveTextureLoder.instance().getIndexContainLocation(WORLDNAME_AND_PATH);
        if (filename != null && ReceiveTextureLoder.CASH_PATH.resolve("cash").resolve(filename).toFile().exists()) {
            ResourceLocation inmap = getPictureImageTexture(IKSGFileLoadUtil.fileBytesReader(ReceiveTextureLoder.CASH_PATH.resolve("cash").resolve(filename)));
            ReceiveTextureLoder.instance().PICTUER_RECEIVE_LOCATION.put(WORLDNAME_AND_PATH, inmap);
            return inmap;
        }
        ReceiveTextureLoder.instance().PICTUER_RECEIVE_LOCATION.put(WORLDNAME_AND_PATH, TEXTUER_LOADING);
        ReceiveTextureLoder.instance().requestTextuerSend(WORLDNAME_AND_PATH, location, name);
        return TEXTUER_LOADING;
    }

    public static ResourceLocation getPictureImageURLTexture(String url) {
        return getPictureImageURLTexture(url, 0, 0);
    }

    public static ResourceLocation getPictureImageURLTexture(String url, int width, int height) {

        URLImageTextureLoder.URLImageData urlid = new URLImageTextureLoder.URLImageData(url, width, height);

        if (URLImageTextureLoder.instance().PICTUER_URL_LOCATION.containsKey(urlid)) {
            return URLImageTextureLoder.instance().PICTUER_URL_LOCATION.get(urlid);
        }

        String filename = URLImageTextureLoder.instance().getIndexContainLocation(urlid);
        if (filename != null && URLImageTextureLoder.CASH_PATH.resolve("cash").resolve(filename).toFile().exists()) {
            ResourceLocation inmap = getPictureImageTexture(IKSGFileLoadUtil.fileBytesReader(URLImageTextureLoder.CASH_PATH.resolve("cash").resolve(filename)));
            URLImageTextureLoder.instance().PICTUER_URL_LOCATION.put(urlid, inmap);
            return inmap;
        }
        URLImageTextureLoder.instance().PICTUER_URL_LOCATION.put(urlid, TEXTUER_LOADING);
        URLImageTextureLoder.instance().downloadTextuer(urlid);
        return TEXTUER_LOADING;
    }


    public static int getWidth(ResourceLocation location, int defalt) {
        Texture tex = mc.textureManager.getTexture(location);

        if (tex instanceof DynamicTexture)
            return Objects.requireNonNull(((DynamicTexture) tex).getTextureData()).getWidth();

        return defalt;
    }

    public static int getHeight(ResourceLocation location, int defalt) {
        Texture tex = mc.textureManager.getTexture(location);

        if (tex instanceof DynamicTexture)
            return Objects.requireNonNull(((DynamicTexture) tex).getTextureData()).getHeight();

        return defalt;
    }

}
