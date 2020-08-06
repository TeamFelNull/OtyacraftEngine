package red.felnull.otyacraftengine.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;

public class IKSGItemStackUtil {
    public static ItemStack createPlayerHead(String name) {
        ItemStack playerhead = new ItemStack(Items.PLAYER_HEAD);
        CompoundNBT tag = playerhead.getOrCreateTag();
        tag.putString("SkullOwner", name);
        return playerhead;
    }

    public static ItemStack createPlayerHead(PlayerEntity player) {
        return createPlayerHead(IKSGPlayerUtil.getUserName(player));
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

    public static ItemStack createMultiGamerHead() {
        return createPlayerHead("MultiGamer8853");
    }

}
