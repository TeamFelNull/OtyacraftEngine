package red.felnull.otyacraftengine.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;

public class ItemStackUtil {
    public static ItemStack createPlayerHead(String name) {
        ItemStack playerhead = new ItemStack(Items.PLAYER_HEAD);
        CompoundNBT tag = playerhead.getOrCreateTag();
        tag.putString("SkullOwner", name);
        return playerhead;
    }

    public static ItemStack createPlayerHead(PlayerEntity player) {
        return createPlayerHead(PlayerHelper.getUserName(player));
    }

    public static ItemStack createMoriMoriHead() {
        return createPlayerHead("MoriMori_0317_jp");
    }

}
