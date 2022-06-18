package dev.felnull.otyacraftengine.client.util;

import com.madgag.gif.fmsware.GifDecoder;
import com.mojang.blaze3d.platform.NativeImage;
import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.client.renderer.texture.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.Tickable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * テクスチャ関係のユーティリティ
 *
 * @author MORIMORI0317
 */
public class OETextureUtils {
    private static final Minecraft mc = Minecraft.getInstance();
    private static final ResourceLocation ERROR_ICON = new ResourceLocation(OtyacraftEngine.MODID, "textures/gui/error_icon.png");
    private static ResourceLocation LOADING_ICON;

    /**
     * ネイティブ画像のテクスチャを読み込む
     *
     * @param stream ストリーム
     * @return テクスチャ
     * @throws IOException 例外
     */
    @NotNull
    public static DynamicTexture createNativeTexture(@NotNull InputStream stream, @Nullable Consumer<TextureLoadProgress> progress) throws IOException {
        if (progress != null)
            progress.accept(new TextureLoadProgressImpl("Image loading", 1, 0));

        byte[] data = stream.readAllBytes();

        if (progress != null)
            progress.accept(new TextureLoadProgressImpl("Image loading", 1, 1));

        try (var istream = new ByteArrayInputStream(data)) {
            GifDecoder decoder = new GifDecoder();
            if (decoder.read(istream) == 0)
                return DynamicGifTexture.create(decoder, progress);
        }

        try (var istream = new ByteArrayInputStream(data)) {
            return new DynamicTexture(NativeImage.read(istream));
        }
    }

    /**
     * テクスチャを開放する
     *
     * @param location テクスチャID
     */
    public synchronized static void freeTexture(@NotNull ResourceLocation location) {
        TextureManager textureManager = mc.getTextureManager();
        AbstractTexture abstractTexture = textureManager.byPath.get(location);
        if (abstractTexture != null) {
            if (abstractTexture instanceof Tickable)
                textureManager.tickableTextures.remove(abstractTexture);
            textureManager.safeClose(location, abstractTexture);
        }
    }

    /**
     * 非同期でネイティブテクスチャの読み込みと取得をする
     *
     * @param uuid   UUID
     * @param stream ストリーム
     * @return テクスチャ結果
     */
    @NotNull
    public static NativeTextureLoadResult getAndLoadNativeTextureAsync(@NotNull UUID uuid, @NotNull InputStream stream) {
        return NativeTextureManager.getInstance().getAndLoadTextureAsync(uuid, stream);
    }

    /**
     * ネイティブテクスチャの読み込みと取得をする
     *
     * @param uuid   UUID
     * @param stream ストリーム
     * @return テクスチャ結果
     */
    @NotNull
    public static NativeTextureLoadResult getAndLoadNativeTexture(@NotNull UUID uuid, @NotNull InputStream stream) {
        return NativeTextureManager.getInstance().getAndLoadTexture(uuid, stream);
    }

    /**
     * ネイティブテクスチャを取得
     *
     * @param uuid UUID
     * @return テクスチャ結果
     */
    @Nullable
    public static NativeTextureLoadResult getNativeTexture(@NotNull UUID uuid) {
        return NativeTextureManager.getInstance().getTexture(uuid);
    }

    /**
     * 非同期でURLテクスチャの読み込みと取得をする
     *
     * @param url    URL
     * @param cached キャッシュするかどうか
     * @return テクスチャ結果
     */
    @NotNull
    public static URLTextureLoadResult getAndLoadURLTextureAsync(@NotNull String url, boolean cached) {
        return URLTextureManager.getInstance().getAndLoadUrlTextureAsync(url, cached);
    }

    /**
     * ロード中アイコンを取得
     *
     * @return ロケーション
     */
    @NotNull
    public static ResourceLocation getLoadingIcon() {
        if (LOADING_ICON == null) {
            ResourceManager rm = mc.getResourceManager();
            var r = rm.getResource(new ResourceLocation(OtyacraftEngine.MODID, "textures/gui/loading_icon.gif"));
            if (r.isPresent()) {
                try (var st = r.get().open()) {
                    var ret = getAndLoadNativeTexture(UUID.randomUUID(), st);
                    if (ret.isSuccess())
                        LOADING_ICON = ret.getLocation();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (LOADING_ICON == null)
                throw new IllegalStateException("Failed to loading icon being loaded.");
        }

        return LOADING_ICON;
    }

    /**
     * エラーアイコン
     *
     * @return ロケーション
     */
    @NotNull
    public static ResourceLocation getErrorIcon() {
        return ERROR_ICON;
    }
}
