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

    @NotNull
    public static GameProfile getPlayerLackProfileTolerance(@NotNull String name) {
        return ClientPlayerInfoManager.getInstance().getLackProfileTolerance(name);
    }

    @NotNull
    public static Optional<UUID> getPlayerUUIDByName(@NotNull String name) {
        return ClientPlayerInfoManager.getInstance().getUUIDByName(name);
    }

    @NotNull
    public static CompletableFuture<Optional<UUID>> getPlayerUUIDByNameAsync(@NotNull String name) {
        return ClientPlayerInfoManager.getInstance().getUUIDByNameAsync(name);
    }

    @NotNull
    public static Optional<String> getPlayerNameByUUID(@NotNull UUID uuid) {
        return ClientPlayerInfoManager.getInstance().getNameByUUID(uuid);
    }

    @NotNull
    public static CompletableFuture<Optional<String>> getPlayerNameByUUIDAsync(@NotNull UUID uuid) {
        return ClientPlayerInfoManager.getInstance().getNameByUUIDAsync(uuid);
    }

    @NotNull
    public PlayerUUIDByNameResult getPlayerUUIDByNameTolerance(@NotNull String name) {
        return ClientPlayerInfoManager.getInstance().getUUIDByNameTolerance(name);
    }

    @NotNull
    public PlayerNameByUUIDResult getPlayerNameByUUIDTolerance(@NotNull UUID uuid) {
        return ClientPlayerInfoManager.getInstance().getNameByUUIDTolerance(uuid);
    }
}
