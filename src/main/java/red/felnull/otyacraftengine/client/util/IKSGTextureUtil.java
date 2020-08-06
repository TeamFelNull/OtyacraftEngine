package red.felnull.otyacraftengine.client.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.data.ReceiveTextureLoder;
import red.felnull.otyacraftengine.util.IKSGFileLoadUtil;
import red.felnull.otyacraftengine.util.IKSGPlayerUtil;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@OnlyIn(Dist.CLIENT)
public class IKSGTextureUtil {
    private static final ResourceLocation LOADING_1 = new ResourceLocation(OtyacraftEngine.MODID, "textures/gui/loading_icon/loading_1.png");
    private static final ResourceLocation LOADING_2 = new ResourceLocation(OtyacraftEngine.MODID, "textures/gui/loading_icon/loading_2.png");
    private static final ResourceLocation LOADING_3 = new ResourceLocation(OtyacraftEngine.MODID, "textures/gui/loading_icon/loading_3.png");
    private static final ResourceLocation LOADING_4 = new ResourceLocation(OtyacraftEngine.MODID, "textures/gui/loading_icon/loading_4.png");
    private static final ResourceLocation TEXTUER_LOADING = new ResourceLocation(OtyacraftEngine.MODID, "textures/gui/textuer_loading.png");
    public static int loadingPaatune;
    private static Minecraft mc = Minecraft.getInstance();
    private static Map<byte[], ResourceLocation> PICTUER_BYTE_LOCATION = new HashMap<byte[], ResourceLocation>();

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
        if (name.equals(mc.player.getName().getString())) {
            return mc.player.getLocationSkin();
        }
        ResourceLocation faselocation;
        GameProfile GP = IKSGPlayerUtil.getPlayerProfile(name);
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
        } catch (Exception e) {
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
        String WORLDNAME_AND_PATH = IKSGClientUtil.getCurrentWorldName() + ":" + location.toString() + ":" + name;

        if (ReceiveTextureLoder.instance().PICTUER_RECEIVE_LOCATION.containsKey(WORLDNAME_AND_PATH)) {
            return ReceiveTextureLoder.instance().PICTUER_RECEIVE_LOCATION.get(WORLDNAME_AND_PATH);
        }

        String filename = ReceiveTextureLoder.instance().getIndexContainLocation(WORLDNAME_AND_PATH);
        if (filename != null && ReceiveTextureLoder.CASH_PATH.resolve("cash").resolve(filename).toFile().exists()) {
            ResourceLocation inmap = getPictureImageTexture(IKSGFileLoadUtil.fileBytesReader(ReceiveTextureLoder.CASH_PATH.resolve("cash").resolve(filename)));
            ReceiveTextureLoder.instance().PICTUER_RECEIVE_LOCATION.put(WORLDNAME_AND_PATH, inmap);
            return inmap;
        }
        ReceiveTextureLoder.instance().requestTextuerSend(WORLDNAME_AND_PATH, location, name);
        ReceiveTextureLoder.instance().PICTUER_RECEIVE_LOCATION.put(WORLDNAME_AND_PATH, TEXTUER_LOADING);
        return TEXTUER_LOADING;
    }
}
