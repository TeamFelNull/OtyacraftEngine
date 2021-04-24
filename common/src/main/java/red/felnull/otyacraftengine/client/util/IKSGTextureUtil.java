package red.felnull.otyacraftengine.client.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import red.felnull.otyacraftengine.util.IKSGPlayerUtil;

import java.util.Map;

public class IKSGTextureUtil {
    private static final Minecraft mc = Minecraft.getInstance();

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
        ResourceLocation faselocation;
        GameProfile GP = IKSGPlayerUtil.getPlayerProfile(name);
        Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = mc.getSkinManager().getInsecureSkinInformation(GP);
        faselocation = map.containsKey(type) ? mc.getSkinManager().registerTexture(map.get(type), type) : DefaultPlayerSkin.getDefaultSkin(Player.createPlayerUUID(GP));
        return faselocation;
    }
}
