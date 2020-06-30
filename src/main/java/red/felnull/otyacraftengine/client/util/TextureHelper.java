package red.felnull.otyacraftengine.client.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import red.felnull.otyacraftengine.util.PlayerHelper;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class TextureHelper {
    private static Minecraft mc = Minecraft.getInstance();

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
        GameProfile GP = PlayerHelper.getPlayerTextuerProfile(name);
        Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = mc.getSkinManager().loadSkinFromCache(GP);
        faselocation = map.containsKey(type) ? mc.getSkinManager().loadSkin(map.get(type), type) : DefaultPlayerSkin.getDefaultSkin(PlayerEntity.getUUID(GP));
        return faselocation;
    }
}
