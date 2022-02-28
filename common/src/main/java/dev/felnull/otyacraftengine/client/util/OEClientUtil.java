package dev.felnull.otyacraftengine.client.util;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.platform.InputConstants;
import dev.felnull.otyacraftengine.client.gui.subtitle.IDynamicSubtitle;
import dev.felnull.otyacraftengine.client.loader.PlayerInfoManager;
import dev.felnull.otyacraftengine.impl.client.OEClientExpectPlatform;
import dev.felnull.otyacraftengine.util.OELangUtil;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.SubtitleOverlay;
import net.minecraft.client.resources.language.LanguageInfo;
import net.minecraft.client.resources.language.LanguageManager;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.tinyfd.TinyFileDialogs;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class OEClientUtil {
    protected static final Map<String, GameProfile> PLAYER_PROFILES = new HashMap<>();
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

    /**
     * キーが押されているか
     *
     * @param keyCode キーコード
     * @return 押されているか
     */
    public static boolean isKeyInput(int keyCode) {
        if (keyCode < GLFW.GLFW_MOUSE_BUTTON_1)
            return false;

        long winID = Minecraft.getInstance().getWindow().getWindow();

        if (keyCode <= GLFW.GLFW_MOUSE_BUTTON_8)
            return GLFW.glfwGetMouseButton(winID, keyCode) == 1;

        return InputConstants.isKeyDown(winID, keyCode);
    }

    /**
     * キーが押されているか
     *
     * @param keyCode キー
     * @return 押されているか
     */
    public static boolean isKeyInput(KeyMapping keyCode) {
        return isKeyInput(OEClientExpectPlatform.getKey(keyCode).getValue());
    }

    /**
     * プレイヤーをUUIDから取得
     *
     * @param uuid プレイヤーUUID
     * @return 名前
     */
    public static Optional<String> getPlayerNameByUUID(@NotNull UUID uuid) {
        if (mc.player != null) {
            var pi = mc.player.connection.getPlayerInfo(uuid);
            if (pi != null)
                return Optional.of(pi.getProfile().getName());
        }
        return PlayerInfoManager.getInstance().getNameByUUID(uuid);
    }

    /**
     * プレイヤーのUUIDを名前から取得
     *
     * @param name プレイヤー名
     * @return uuid
     */
    public static Optional<UUID> getPlayerUUIDByName(@NotNull String name) {
        var pi = mc.player.connection.getPlayerInfo(name);
        if (pi != null)
            return Optional.of(pi.getProfile().getId());
        return PlayerInfoManager.getInstance().getUUIDByName(name);
    }

    /**
     * GoogleLangCodeから言語に変換
     *
     * @param googleCode GoogleLangCode
     * @return 言語
     */
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

    /**
     * 言語からGoogleLangCodeへ変換
     *
     * @param language 言語
     * @return GoogleLangCode
     */
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

    /**
     * 字幕を追加
     *
     * @param subtitle  字幕
     * @param duplicate 　複製可能か
     */
    public static void addSubtitle(SubtitleOverlay.Subtitle subtitle, boolean duplicate) {
        var subs = mc.gui.subtitleOverlay.subtitles;
        if (!duplicate) {
            for (SubtitleOverlay.Subtitle sub : subs) {
                if (sub.getText().equals(subtitle.getText())) {
                    sub.refresh(subtitle.getLocation());
                    ((IDynamicSubtitle) sub).setDynamicLocation(((IDynamicSubtitle) subtitle).getDynamicLocation());
                    return;
                }
            }
        }
        subs.add(subtitle);
    }

    @Nullable
    public static File[] openFileChooser(String title, @Nullable Path defaultPath, @Nullable String singleFilter, boolean allowMultipleSelects) {
        var st = TinyFileDialogs.tinyfd_openFileDialog(title, defaultPath != null ? defaultPath.toString() : null, null, singleFilter, allowMultipleSelects);
        if (st == null) return null;
        try {
            if (allowMultipleSelects) {
                String[] sp = st.split("\\|");
                File[] fl = new File[sp.length];
                for (int i = 0; i < sp.length; i++) {
                    fl[i] = new File(sp[i]);
                }
                return fl;
            } else {
                return new File[]{new File(st)};
            }
        } catch (Exception ex) {
            return null;
        }
    }
}
