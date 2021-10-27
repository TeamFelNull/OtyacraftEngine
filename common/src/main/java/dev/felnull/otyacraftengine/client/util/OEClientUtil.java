package dev.felnull.otyacraftengine.client.util;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.platform.InputConstants;
import dev.felnull.otyacraftengine.impl.client.OEClientExpectPlatform;
import dev.felnull.otyacraftengine.util.OELangUtil;
import dev.felnull.otyacraftengine.util.OEPlayerUtil;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.LanguageInfo;
import net.minecraft.client.resources.language.LanguageManager;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import org.lwjgl.glfw.GLFW;

import java.util.*;

public class OEClientUtil {
    protected static final Map<String, GameProfile> PLAYER_PROFILES = new HashMap<>();
    protected static final Map<UUID, String> PLAYER_NAME_UUIDS = new HashMap<>();
    protected static final List<UUID> LOADING_PLAYER_NAMES = new ArrayList<>();
    private static final Minecraft mc = Minecraft.getInstance();

    /**
     * "
     * クライアントでプレイヤープロフィールを取得する
     *
     * @param name プレイヤー名
     * @return プロフィール
     */
    public static GameProfile getClientPlayerProfile(String name) {
        if (PLAYER_PROFILES.containsKey(name))
            return PLAYER_PROFILES.get(name);

        GameProfile gp = new GameProfile(null, name);
        PLAYER_PROFILES.put(name, gp);
        SkullBlockEntity.updateGameprofile(gp, n -> PLAYER_PROFILES.put(name, n));
        return gp;
    }

    public static boolean isKeyInput(int keyCode) {
        if (keyCode < GLFW.GLFW_MOUSE_BUTTON_1)
            return false;

        long winID = Minecraft.getInstance().getWindow().getWindow();

        if (keyCode <= GLFW.GLFW_MOUSE_BUTTON_8)
            return GLFW.glfwGetMouseButton(winID, keyCode) == 1;

        return InputConstants.isKeyDown(winID, keyCode);
    }

    public static boolean isKeyInput(KeyMapping keyCode) {
        return isKeyInput(OEClientExpectPlatform.getKey(keyCode).getValue());
    }

    public static Optional<String> getPlayerNameByUUID(UUID uuid) {
        if (mc.player.connection.getPlayerInfo(uuid) != null)
            return Optional.of(mc.player.connection.getPlayerInfo(uuid).getProfile().getName());

        if (PLAYER_NAME_UUIDS.containsKey(uuid))
            return Optional.of(PLAYER_NAME_UUIDS.get(uuid));

        if (LOADING_PLAYER_NAMES.contains(uuid))
            return Optional.empty();

        LOADING_PLAYER_NAMES.contains(uuid);
        OEPlayerUtil.getNameByUUIDAsync(uuid, n -> mc.submit(() -> {
            PLAYER_NAME_UUIDS.put(uuid, n.orElse(null));
            LOADING_PLAYER_NAMES.remove(uuid);
        }));
        return Optional.empty();
    }

    public static LanguageInfo getLanguageByGoogleCode(String googleCode) {
        var str = OELangUtil.getLangIdByGoogleCode(googleCode);
        if (str.isPresent())
            return mc.getLanguageManager().getLanguage(str.get());

        for (LanguageInfo language : mc.getLanguageManager().getLanguages()) {
            if (language.getCode().split("_")[0].equals(googleCode))
                return language;
        }

        return mc.getLanguageManager().getLanguage(LanguageManager.DEFAULT_LANGUAGE_CODE);
    }

    public static String getGoogleCodeByLanguage(LanguageInfo language) {
        var lng = OELangUtil.getGoogleCodeByLangId(language.getCode());
        if (lng.isPresent())
            return lng.get();

        String name = language.getCode().split("_")[0];
        for (String lang : OELangUtil.googleLangCodes) {
            if (lang.equals(name))
                return lang;
        }

        return "en";
    }
}
