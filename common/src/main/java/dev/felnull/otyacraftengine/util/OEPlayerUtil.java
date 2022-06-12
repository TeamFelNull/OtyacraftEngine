package dev.felnull.otyacraftengine.util;

import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.chunk.LevelChunk;

import java.util.function.Consumer;

/**
 * プレイヤー関係のユーティリティ
 *
 * @author MORIMORI0317
 */
public class OEPlayerUtil {
    /**
     * チャンクのプレイヤーに対しての処理
     *
     * @param chunk          チャンク
     * @param playerConsumer プレイヤーの処理
     */
    public static void doPlayers(LevelChunk chunk, Consumer<ServerPlayer> playerConsumer) {
        ((ServerChunkCache) chunk.getLevel().getChunkSource()).chunkMap.getPlayers(chunk.getPos(), false).forEach(playerConsumer);
    }

    /**
     * プレイヤーにアイテムを渡す
     * インベントリが埋まっている場合はドロップさせる
     *
     * @param player プレイヤー
     * @param stack  アイテムスタック
     */
    public static void giveItem(ServerPlayer player, ItemStack stack) {
        if (!player.addItem(stack))
            player.drop(stack, false, true);
    }
}
