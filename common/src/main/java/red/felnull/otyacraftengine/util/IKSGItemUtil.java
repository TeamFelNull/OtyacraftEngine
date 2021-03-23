package red.felnull.otyacraftengine.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class IKSGItemUtil {
    public static ItemStack copyStackWithSize(ItemStack itemStack, int size) {
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

    public static ItemStack createPlayerHead(Player player) {
        return createPlayerHead(player.getGameProfile().getName());
    }

}
