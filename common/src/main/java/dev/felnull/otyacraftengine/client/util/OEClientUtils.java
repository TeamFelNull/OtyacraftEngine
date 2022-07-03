package dev.felnull.otyacraftengine.client.util;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.platform.InputConstants;
import dev.felnull.otyacraftengine.client.entity.ClientPlayerInfoManager;
import dev.felnull.otyacraftengine.client.entity.PlayerNameByUUIDResult;
import dev.felnull.otyacraftengine.client.entity.PlayerUUIDByNameResult;
import dev.felnull.otyacraftengine.explatform.client.OEClientExpectPlatform;
import net.minecraft.client.KeyMapping;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * クライアントのユーティリティ
 *
 * @author MORIMORI0317
 */
public class OEClientUtils {
    /**
     * 特定の時間でループする値
     *
     * @param loopTime 0.0から1.0までの時間
     * @return 0.0~1.0までの値
     */
    public static float getParSecond(long loopTime) {
        return (float) (System.currentTimeMillis() % loopTime) / (float) loopTime;
    }

    /**
     * keyMappingからkeyを取得
     *
     * @param keyMapping 対象
     * @return key
     */
    public static InputConstants.Key getKey(@NotNull KeyMapping keyMapping) {
        return OEClientExpectPlatform.getKey(keyMapping);
    }

    /**
     * プレイヤー名から一部欠落したプロファイルを読み込む
     * ほとんどテクスチャ取得用
     *
     * @param name プレイヤー名
     * @return プロファイ
     */
    @NotNull
    public static GameProfile getPlayerLackProfileTolerance(@NotNull String name) {
        return ClientPlayerInfoManager.getInstance().getLackProfileTolerance(name);
    }

    /**
     * プレイヤー名からプレイヤーUUIDを取得する
     * {@link dev.felnull.otyacraftengine.util.OEPlayerUtils#getUUIDByName(String)}との違いはクライアント側で取得できるプレイヤーからも検索する
     *
     * @param name プレイヤー名
     * @return UUID
     */
    @NotNull
    public static Optional<UUID> getPlayerUUIDByName(@NotNull String name) {
        return ClientPlayerInfoManager.getInstance().getUUIDByName(name);
    }

    /**
     * 非同期でプレイヤー名からプレイヤーUUIDを取得する
     * {@link dev.felnull.otyacraftengine.util.OEPlayerUtils#getUUIDByNameAsync(String)}との違いはクライアント側で取得できるプレイヤーからも検索する
     *
     * @param name プレイヤー名
     * @return UUID
     */
    @NotNull
    public static CompletableFuture<Optional<UUID>> getPlayerUUIDByNameAsync(@NotNull String name) {
        return ClientPlayerInfoManager.getInstance().getUUIDByNameAsync(name);
    }

    /**
     * プレイヤーUUIDからプレイヤー名を取得する
     * {@link dev.felnull.otyacraftengine.util.OEPlayerUtils#getNameByUUID(UUID)}との違いはクライアント側で取得できるプレイヤーからも検索する
     *
     * @param uuid プレイヤーUUID
     * @return 名前
     */
    @NotNull
    public static Optional<String> getPlayerNameByUUID(@NotNull UUID uuid) {
        return ClientPlayerInfoManager.getInstance().getNameByUUID(uuid);
    }

    /**
     * 非同期でプレイヤーUUIDからプレイヤー名を取得する
     * {@link dev.felnull.otyacraftengine.util.OEPlayerUtils#getNameByUUIDAsync(UUID)}との違いはクライアント側で取得できるプレイヤーからも検索する
     *
     * @param uuid プレイヤーUUID
     * @return 名前
     */
    @NotNull
    public static CompletableFuture<Optional<String>> getPlayerNameByUUIDAsync(@NotNull UUID uuid) {
        return ClientPlayerInfoManager.getInstance().getNameByUUIDAsync(uuid);
    }

    /**
     * プレイヤー名からプレイヤーUUIDを取得する
     * {@link #getPlayerUUIDByName(String)}との違いは取得失敗時や、取得中でも即座に結果を返す
     * 描画など即座に出力するために利用
     *
     * @param name プレイヤー名
     * @return 取得結果
     */
    @NotNull
    public PlayerUUIDByNameResult getPlayerUUIDByNameTolerance(@NotNull String name) {
        return ClientPlayerInfoManager.getInstance().getUUIDByNameTolerance(name);
    }

    /**
     * プレイヤーUUIDからプレイヤー名を取得する
     * {@link #getPlayerNameByUUID(UUID)}との違いは取得失敗時や、取得中でも即座に結果を返す
     * 描画など即座に出力するために利用
     *
     * @param uuid プレイヤーUUID
     * @return 取得結果
     */
    @NotNull
    public PlayerNameByUUIDResult getPlayerNameByUUIDTolerance(@NotNull UUID uuid) {
        return ClientPlayerInfoManager.getInstance().getNameByUUIDTolerance(uuid);
    }
}
