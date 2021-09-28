package dev.felnull.otyacraftengine.client.util;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import dev.felnull.otyacraftengine.util.OEPlayerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OETextureUtil {
    private static final Minecraft mc = Minecraft.getInstance();
    private static final Map<UUID, String> UUID_PLAYER_NAMES = new HashMap<>();

    @Nullable
    public static ResourceLocation getPlayerTexture(MinecraftProfileTexture.Type type, String name) {
        if (name == null)
            return null;
        if (mc.player != null && mc.player.connection.getPlayerInfo(name) != null) {
            return switch (type) {
                case SKIN -> mc.player.connection.getPlayerInfo(name).getSkinLocation();
                case CAPE -> mc.player.connection.getPlayerInfo(name).getCapeLocation();
                case ELYTRA -> mc.player.connection.getPlayerInfo(name).getElytraLocation();
            };
        }
        var gameProfile = OEClientUtil.getClientPlayerProfile(name);
        var tex = mc.getSkinManager().getInsecureSkinInformation(gameProfile).get(type);
        return tex != null ? mc.getSkinManager().registerTexture(tex, type) : type == MinecraftProfileTexture.Type.SKIN ? DefaultPlayerSkin.getDefaultSkin(Player.createPlayerUUID(gameProfile)) : null;
    }

    public static ResourceLocation getPlayerTexture(MinecraftProfileTexture.Type type, UUID uuid) {
        if (mc.player != null && mc.player.connection.getPlayerInfo(uuid) != null) {
            return switch (type) {
                case SKIN -> mc.player.connection.getPlayerInfo(uuid).getSkinLocation();
                case CAPE -> mc.player.connection.getPlayerInfo(uuid).getCapeLocation();
                case ELYTRA -> mc.player.connection.getPlayerInfo(uuid).getElytraLocation();
            };
        }

        String name = UUID_PLAYER_NAMES.get(uuid);

        if (name == null) {
            UUID_PLAYER_NAMES.put(uuid, "");
            OEPlayerUtil.getNameByUUIDAsync(uuid, n -> mc.submit(() -> {
                UUID_PLAYER_NAMES.put(uuid, n != null ? n : uuid.toString());
            }));
            return type == MinecraftProfileTexture.Type.SKIN ? DefaultPlayerSkin.getDefaultSkin(uuid) : null;
        } else if (name.isEmpty()) {
            return type == MinecraftProfileTexture.Type.SKIN ? DefaultPlayerSkin.getDefaultSkin(uuid) : null;
        }

        return getPlayerTexture(type, name);
    }
}
