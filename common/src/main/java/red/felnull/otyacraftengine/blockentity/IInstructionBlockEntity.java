package red.felnull.otyacraftengine.blockentity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;

/**
 * クライアントから簡単にブロックエンティティにパケットを送り付けられる
 *
 * @author MORIMORI0317
 * @since 1.8
 */
public interface IInstructionBlockEntity {
    /**
     * クライアントからの指示
     *
     * @param player 指示するプレイヤー
     * @param name   指示名
     * @param data   指示内容
     * @return 返信
     */
    CompoundTag instructionFromClient(ServerPlayer player, String name, CompoundTag data);

    /**
     * クライアントから支持を受けられるかどうか
     *
     * @param player 指示するプレイヤー
     * @param name   指示名
     * @param data   指示内容
     * @return 指示を受けるかどうか
     */
    boolean canInstructionWith(ServerPlayer player, String name, CompoundTag data);
}
