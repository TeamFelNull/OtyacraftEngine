package dev.felnull.otyacraftengine.client.util;

import com.madgag.gif.fmsware.GifDecoder;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.blaze3d.platform.NativeImage;
import dev.felnull.fnjl.util.FNDataUtil;
import dev.felnull.fnjl.util.FNImageUtil;
import dev.felnull.fnjl.util.FNURLUtil;
import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.client.renderer.texture.DynamicGifTexture;
import dev.felnull.otyacraftengine.impl.client.OEClientExpectPlatform;
import dev.felnull.otyacraftengine.util.OEPaths;
import dev.felnull.otyacraftengine.util.OEPlayerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

public class OETextureUtil {
    private static final Minecraft mc = Minecraft.getInstance();
    protected static final Map<UUID, TextureLoadResult> NATIVE_TEXTURES = new HashMap<>();
    protected static final List<UUID> LOAD_TEXTURES = new ArrayList<>();
    protected static ResourceLocation LOADING_ICON;
    private static final Map<UUID, String> UUID_PLAYER_NAMES = new HashMap<>();
    protected static final Map<String, String> URL_FILENAME_INDEX = new HashMap<>();
    protected static final Map<String, UUID> URL_TEXTURES_UUIDS = new HashMap<>();

    /**
     * ロード中アイコンを取得
     *
     * @return ロケーション
     */
    public static ResourceLocation getLoadingIcon() {
        if (LOADING_ICON == null) {
            ResourceManager rm = mc.getResourceManager();
            try {
                LOADING_ICON = OETextureUtil.loadNativeTexture(rm.getResource(new ResourceLocation(OtyacraftEngine.MODID, "textures/gui/loading.gif")).getInputStream(), MissingTextureAtlasSprite.getLocation()).location();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return LOADING_ICON;
    }

    /**
     * UUIDからプレイヤースキンテクスチャ取得
     * 存在しない場合はnullを返す
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
     * 存在しない場合はnullを返す
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
     * 存在しない場合はnullを返す
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
     * 存在しない場合はnullを返す
     *
     * @param name プレイヤー名
     * @return プレイヤースキンテクスチャID
     */
    public static ResourceLocation getPlayerSkinTexture(String name) {
        return getPlayerTexture(MinecraftProfileTexture.Type.SKIN, name);
    }

    /**
     * 名前からプレイヤーマントテクスチャ取得
     * 存在しない場合はnullを返す
     *
     * @param name プレイヤー名
     * @return プレイヤーマントテクスチャID
     */
    public static ResourceLocation getPlayerCapeTexture(String name) {
        return getPlayerTexture(MinecraftProfileTexture.Type.CAPE, name);
    }

    /**
     * 名前からプレイヤーエリトラテクスチャ取得
     * 存在しない場合はnullを返す
     *
     * @param name プレイヤー名
     * @return プレイヤーエリトラテクスチャID
     */
    public static ResourceLocation getPlayerElytraTexture(String name) {
        return getPlayerTexture(MinecraftProfileTexture.Type.ELYTRA, name);
    }

    /**
     * 名前からプレイヤーテクスチャ取得
     * 存在しない場合はnullを返す
     *
     * @param type テクスチャタイプ
     * @param name プレイヤー名
     * @return プレイヤーテクスチャロケーション
     */
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

    /**
     * UUIDからプレイヤーテクスチャ取得
     * 存在しない場合はnullを返す
     *
     * @param type テクスチャタイプ
     * @param uuid プレイヤーUUID
     * @return プレイヤーテクスチャロケーション
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

    public static ResourceLocation getURLTexture(String url, boolean cash, ResourceLocation failureTexture) {
        if (URL_TEXTURES_UUIDS.containsKey(url))
            return getNativeTexture(URL_TEXTURES_UUIDS.get(url), null);

        UUID id = UUID.randomUUID();
        TextureLoadResult tx = loadURLTexture(id, url, cash, failureTexture);
        URL_TEXTURES_UUIDS.put(url, id);
        NATIVE_TEXTURES.put(id, tx);
        return tx.location();
    }

    private static TextureLoadResult loadURLTexture(UUID uuid, String url, boolean cash, ResourceLocation failureTexture) {
        try {
            InputStream stream;
            byte[] md5 = FNDataUtil.createMD5Hash(url.getBytes(StandardCharsets.UTF_8));
            String identification = Base64.getEncoder().encodeToString(md5);
            Path oep = OEPaths.getClientOEFolderPath().resolve("url_textures");
            if (URL_FILENAME_INDEX.containsKey(identification) && oep.resolve(URL_FILENAME_INDEX.get(identification)).toFile().exists()) {
                stream = new BufferedInputStream(new FileInputStream(oep.resolve(URL_FILENAME_INDEX.get(identification)).toFile()));
            } else {
                HttpURLConnection connection = FNURLUtil.getConnection(new URL(url));
                long length = connection.getContentLengthLong();
                long max = 1024L * 1024L;
                if (length > max)
                    throw new IOException("Size Over: " + max + "byte" + " current: " + length + "byte");
                if (cash) {
                    byte[] data = connection.getInputStream().readAllBytes();
                    UUID uid = UUID.randomUUID();
                    Files.write(oep.resolve(uid.toString()), data);
                    URL_FILENAME_INDEX.put(identification, uid.toString());
                    stream = new ByteArrayInputStream(data);
                } else {
                    stream = connection.getInputStream();
                }
            }
            return loadNativeTexture(stream, failureTexture);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new TextureLoadResult(failureTexture, true);
    }

    /**
     * 非同期でネイティブテクスチャを取得する
     *
     * @param id     テクスチャ管理ID
     * @param stream リソース
     * @return テクスチャID
     */
    public static ResourceLocation getNativeTextureAsyncLoad(UUID id, InputStream stream) {
        return getNativeTextureAsyncLoad(id, stream, MissingTextureAtlasSprite.getLocation());
    }

    /**
     * 非同期でネイティブテクスチャを取得する
     *
     * @param id             テクスチャ管理ID
     * @param stream         リソース
     * @param failureTexture 読み込み失敗時のテクスチャ
     * @return テクスチャID
     */
    public static ResourceLocation getNativeTextureAsyncLoad(UUID id, InputStream stream, ResourceLocation failureTexture) {
        return getNativeTextureAsyncLoad(id, stream, getLoadingIcon(), failureTexture);
    }

    /**
     * 非同期でネイティブテクスチャを取得する
     *
     * @param id             テクスチャ管理ID
     * @param stream         リソース
     * @param loadTexture    ロード中のテクスチャ
     * @param failureTexture 読み込み失敗時のテクスチャ
     * @return テクスチャID
     */
    public static ResourceLocation getNativeTextureAsyncLoad(UUID id, InputStream stream, ResourceLocation loadTexture, ResourceLocation failureTexture) {
        if (NATIVE_TEXTURES.containsKey(id))
            return NATIVE_TEXTURES.get(id).location();

        if (LOAD_TEXTURES.contains(id))
            return loadTexture;

        CompletableFuture.runAsync(() -> {
            LOAD_TEXTURES.add(id);
            var tex = loadNativeTexture(stream, failureTexture);
            mc.submit(() -> {
                NATIVE_TEXTURES.put(id, tex);
                LOAD_TEXTURES.remove(id);
            });
        });

        return loadTexture;
    }

    /**
     * 非同期でネイティブテクスチャを取得する
     *
     * @param id     テクスチャ管理ID
     * @param stream リソース
     * @return テクスチャID
     */
    public static ResourceLocation getNativeTexture(UUID id, InputStream stream) {
        return getNativeTexture(id, stream, MissingTextureAtlasSprite.getLocation());
    }

    /**
     * 非同期でネイティブテクスチャを取得する
     *
     * @param id             テクスチャ管理ID
     * @param stream         リソース
     * @param failureTexture 読み込み失敗時のテクスチャ
     * @return テクスチャID
     */
    public static ResourceLocation getNativeTexture(UUID id, InputStream stream, ResourceLocation failureTexture) {
        if (NATIVE_TEXTURES.containsKey(id))
            return NATIVE_TEXTURES.get(id).location();

        var tex = loadNativeTexture(stream, failureTexture);
        NATIVE_TEXTURES.put(id, tex);
        return tex.location();
    }

    protected static TextureLoadResult loadNativeTexture(InputStream stream, ResourceLocation failureTexture) {
        try {
            byte[] data = stream.readAllBytes();
            GifDecoder decoder = new GifDecoder();
            boolean gifFlg = decoder.read(new ByteArrayInputStream(data)) == 0;
            AtomicReference<ResourceLocation> tex = new AtomicReference<>();
            if (!gifFlg) {
                NativeImage ni = NativeImage.read(new ByteArrayInputStream(data));
                mc.submit(() -> tex.set(mc.getTextureManager().register("native_texture", new DynamicTexture(ni)))).get();
            } else {
                DynamicGifTexture.ImageFrame[] frames = new DynamicGifTexture.ImageFrame[decoder.getFrameCount()];
                long duration = 0;
                for (int i = 0; i < decoder.getFrameCount(); i++) {
                    frames[i] = new DynamicGifTexture.ImageFrame(NativeImage.read(FNImageUtil.toInputStream(decoder.getFrame(i), "png")), decoder.getDelay(i));
                    duration += decoder.getDelay(i);
                }
                long finalDuration = duration;
                mc.submit(() -> tex.set(mc.getTextureManager().register("native_texture", new DynamicGifTexture(finalDuration, frames)))).get();
            }
            return new TextureLoadResult(tex.get(), false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new TextureLoadResult(failureTexture, true);
    }

    /**
     * ネイティブテクスチャを開放する
     *
     * @param id テクスチャ管理ID
     */
    public static void freeNativeTexture(UUID id) {
        if (NATIVE_TEXTURES.containsKey(id)) {
            if (!NATIVE_TEXTURES.get(id).failure())
                freeTexture(NATIVE_TEXTURES.get(id).location());
            NATIVE_TEXTURES.remove(id);
        }
    }

    /**
     * テクスチャを開放する
     *
     * @param location テクスチャID
     */
    public static void freeTexture(ResourceLocation location) {
        OEClientExpectPlatform.freeTexture(location);
    }

    private static record TextureLoadResult(ResourceLocation location, boolean failure) {
    }
}
