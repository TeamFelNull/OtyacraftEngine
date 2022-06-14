package dev.felnull.otyacraftengine.util;

import com.google.common.collect.ImmutableList;
import dev.felnull.otyacraftengine.explatform.OEExpectPlatform;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * アイテム関係のユーティリティ
 *
 * @author MORIMORI0317
 */
public class OEItemUtils {
    /**
     * MOBバケツから中身のエンティティタイプを取得
     *
     * @param item MOBバケツのアイテム
     * @return アイテムスタック
     */
    public static EntityType<?> getMobBucketEntity(@NotNull MobBucketItem item) {
        return OEExpectPlatform.getMobBucketEntity(item);
    }

    /**
     * アイテムスタックをコピーして、スタック数を制定する
     *
     * @param itemStack アイテムスタック
     * @param size      スタック数
     * @return コピーずみアイテムスタック
     */
    @NotNull
    public static ItemStack copyAndSetCount(@NotNull ItemStack itemStack, int size) {
        if (size == 0)
            return ItemStack.EMPTY;
        ItemStack copy = itemStack.copy();
        copy.setCount(size);
        return copy;
    }

    @NotNull
    public static ItemStack createMoriMoriHead() {
        return createPlayerHead("MoriMori_0317_jp");
    }

    @NotNull
    public static ItemStack createKamesutaHead() {
        return createPlayerHead("kamesuta");
    }

    @NotNull
    public static ItemStack createNinHead() {
        return createPlayerHead("nin8995");
    }

    @NotNull
    public static ItemStack createMGHead() {
        return createPlayerHead("MultiGamer8853");
    }

    @NotNull
    public static ItemStack createMiyabiHead() {
        return createPlayerHead("miyabi0333");
    }

    @NotNull
    public static ItemStack createYuuHead() {
        return createPlayerHead("yuu_111");
    }

    @NotNull
    public static ItemStack createToranpfanHead() {
        return createPlayerHead("toranpfan6433");
    }

    @NotNull
    public static ItemStack createHarumakiHead() {
        return createPlayerHead("Harumaki_jp");
    }

    @NotNull
    public static ItemStack createBuunnHead() {
        return createPlayerHead("yamane_buunn0921");
    }

    @NotNull
    public static ItemStack createMUHead() {
        return createPlayerHead("MU_2525");
    }

    @NotNull
    public static ItemStack createAlfortHead() {
        return createPlayerHead("Alfort121R");
    }

    /**
     * プレイヤーの頭のアイテムスタックを作成
     *
     * @param player プレイヤー
     * @return 頭のアイテムスタック
     */
    @NotNull
    public static ItemStack createPlayerHead(@NotNull Player player) {
        return createPlayerHead(player.getGameProfile().getName());
    }

    /**
     * プレイヤーの頭のアイテムスタックを作成
     *
     * @param name プレイヤー名
     * @return 頭のアイテムスタック
     */
    @NotNull
    public static ItemStack createPlayerHead(@NotNull String name) {
        ItemStack playerhead = new ItemStack(Items.PLAYER_HEAD);
        CompoundTag tag = playerhead.getOrCreateTag();
        tag.putString("SkullOwner", name);
        return playerhead;
    }

    /**
     * アイテムスタックエンティティを作成
     *
     * @param item  アイテムスタック
     * @param level レベル
     * @param pos   座標
     * @return アイテムスタックエンティティ
     */
    @NotNull
    public static ItemEntity createItemEntity(@NotNull ItemStack item, @NotNull Level level, Vec3 pos) {
        return createItemEntity(item, level, pos.x(), pos.y(), pos.z());
    }

    /**
     * アイテムスタックエンティティを作成
     *
     * @param item  アイテムスタック
     * @param level レベル
     * @param x     座標X
     * @param y     座標Y
     * @param z     座標Z
     * @return アイテムスタックエンティティ
     */
    @NotNull
    public static ItemEntity createItemEntity(@NotNull ItemStack item, @NotNull Level level, double x, double y, double z) {
        ItemEntity iteme = new ItemEntity(level, x, y, z, item);
        iteme.setDefaultPickUpDelay();
        return iteme;
    }

    /**
     * 指定の数だけのスタックされたアイテムスタックのリストを用意する
     *
     * @param stack アイテムスタック
     * @param cont  　数
     * @return アイテムスタックのリスト
     */
    @NotNull
    public static List<ItemStack> allocationItemStack(@NotNull ItemStack stack, int cont) {
        if (stack.isEmpty()) return ImmutableList.of();
        List<ItemStack> stacks = new ArrayList<>();
        int sc = stack.getMaxStackSize();
        int ct = cont / sc;
        int am = cont - ct * sc;
        for (int i = 0; i < ct; i++) {
            var st = stack.copy();
            st.setCount(sc);
            stacks.add(st);
        }
        if (am != 0) {
            var st = stack.copy();
            st.setCount(am);
            stacks.add(st);
        }
        return ImmutableList.copyOf(stacks);
    }

    /**
     * アイテムスタックからMODIDを取得
     *
     * @param stack アイテムスタック
     * @return MODID
     */
    public static String getCreatorModId(@NotNull ItemStack stack) {
        return OEExpectPlatform.getItemCreatorModId(stack);
    }
}
