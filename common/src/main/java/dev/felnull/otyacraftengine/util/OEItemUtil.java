package dev.felnull.otyacraftengine.util;

import com.google.common.collect.Lists;
import dev.felnull.otyacraftengine.impl.OEExpectPlatform;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class OEItemUtil {
    public static EntityType<?> getMobBucketEntity(MobBucketItem item) {
        return OEExpectPlatform.getMobBucketEntity(item);
    }

    public static ItemStack copyStackSetSize(ItemStack itemStack, int size) {
        if (size == 0)
            return ItemStack.EMPTY;
        ItemStack copy = itemStack.copy();
        copy.setCount(size);
        return copy;
    }

    public static ItemStack createPlayerHead(String name) {
        ItemStack playerhead = new ItemStack(Items.PLAYER_HEAD);
        CompoundTag tag = playerhead.getOrCreateTag();
        tag.putString("SkullOwner", name);
        return playerhead;
    }

    public static ItemStack createMoriMoriHead() {
        return createPlayerHead("MoriMori_0317_jp");
    }

    public static ItemStack createKamesutaHead() {
        return createPlayerHead("kamesuta");
    }

    public static ItemStack createNinHead() {
        return createPlayerHead("nin8995");
    }

    public static ItemStack createMGHead() {
        return createPlayerHead("MultiGamer8853");
    }

    public static ItemStack createMiyabiHead() {
        return createPlayerHead("miyabi0333");
    }

    public static ItemStack createYuuHead() {
        return createPlayerHead("yuu_111");
    }

    public static ItemStack createPlayerHead(Player player) {
        return createPlayerHead(player.getGameProfile().getName());
    }

    public static ItemEntity createItemEntity(ItemStack item, Level level, double x, double y, double z) {
        ItemEntity iteme = new ItemEntity(level, x, y, z, item);
        iteme.setDefaultPickUpDelay();
        return iteme;
    }

    public static ItemEntity spawnItemEntity(ItemStack item, Level level, double x, double y, double z) {
        ItemEntity entity = createItemEntity(item, level, x, y, z);
        level.addFreshEntity(entity);
        return entity;
    }

    public static List<ItemStack> divideItemStack(ItemStack stack, int cont) {
        if (stack.isEmpty()) return Lists.newArrayList();
        List<ItemStack> stacks = new ArrayList<>();
        int ct = cont / 64;
        int am = cont - ct * 64;
        for (int i = 0; i < ct; i++) {
            var st = stack.copy();
            st.setCount(64);
            stacks.add(st);
        }
        if (am != 0) {
            var st = stack.copy();
            st.setCount(am);
            stacks.add(st);
        }
        return stacks;
    }
}
