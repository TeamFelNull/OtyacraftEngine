package dev.felnull.otyacraftengine.util;

import dev.felnull.otyacraftengine.entity.PlayerInfoManager;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * プレイヤー関係のユーティリティ
 *
 * @author MORIMORI0317
 */
public class OEPlayerUtils {
    /**
     * チャンクのプレイヤーに対しての処理
     *
     * @param chunk          チャンク
     * @param playerConsumer プレイヤーの処理
     */
    public static void doPlayers(@NotNull LevelChunk chunk, @NotNull Consumer<ServerPlayer> playerConsumer) {
        ((ServerChunkCache) chunk.getLevel().getChunkSource()).chunkMap.getPlayers(chunk.getPos(), false).forEach(playerConsumer);
    }

    /**
     * プレイヤーにアイテムを渡す
     * インベントリが埋まっている場合はドロップさせる
     *
     * @param player プレイヤー
     * @param stack  アイテムスタック
     */
    public static void giveItem(@NotNull ServerPlayer player, @NotNull ItemStack stack) {
        if (!player.addItem(stack))
            player.drop(stack, false, true);
    }

    /**
     * プレイヤー名からUUIDを取得
     * 取得失敗や、無効な名前の場合はempty
     *
     * @param name プレイヤー名
     * @return UUID
     */
    @NotNull
    public static Optional<UUID> getUUIDByName(@NotNull String name) {
        return PlayerInfoManager.getInstance().getUUIDByNameCached(name);
    }

    /**
     * UUIDからプレイヤー名を取得
     * 取得失敗や、無効な名前の場合はempty
     *
     * @param uuid UUID
     * @return プレイヤー名
     */
    @NotNull
    public static Optional<String> getNameByUUID(@NotNull UUID uuid) {
        return PlayerInfoManager.getInstance().getNameByUUIDCached(uuid);
    }

    /**
     * 非同期でプレイヤー名からUUIDを取得
     * 取得失敗や、無効な名前の場合はempty
     *
     * @param name プレイヤー名
     * @return UUIDCompletableFuture
     */
    @NotNull
    public static CompletableFuture<Optional<UUID>> getUUIDByNameAsync(@NotNull String name) {
        return PlayerInfoManager.getInstance().getUUIDByNameCachedAsync(name);
    }

    /**
     * 非同期でUUIDからプレイヤー名を取得
     * 取得失敗や、無効な名前の場合はempty
     *
     * @param uuid UUID
     * @return プレイヤー名CompletableFuture
     */
    @NotNull
    public static CompletableFuture<Optional<String>> getNameByUUIDAsync(@NotNull UUID uuid) {
        return PlayerInfoManager.getInstance().getNameByUUIDCachedAsync(uuid);
    }
}