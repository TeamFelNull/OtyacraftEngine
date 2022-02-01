package dev.felnull.otyacraftengine.networking;

import dev.felnull.otyacraftengine.item.IInstructionItem;
import dev.felnull.otyacraftengine.item.location.IPlayerItemLocation;
import dev.felnull.otyacraftengine.item.location.PlayerItemLocations;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public record ItemExistence(IPlayerItemLocation location, ResourceLocation itemLocation) {
    public static ItemExistence readFBB(FriendlyByteBuf buf) {
        return new ItemExistence(PlayerItemLocations.create(buf.readResourceLocation(), buf.readNbt()), buf.readResourceLocation());
    }

    public static ItemExistence getByItemLocation(ItemStack stack, IPlayerItemLocation location) {
        var itm = Registry.ITEM.getKey(stack.getItem());
        return new ItemExistence(location, itm);
    }

    public boolean check(Player player) {
        if (location == null) return false;
        var litm = location.getItem(player);
        if (litm.isEmpty()) return false;
        var itm = Registry.ITEM.get(itemLocation);
        return litm.getItem() == itm && itm instanceof IInstructionItem;
    }

    public FriendlyByteBuf writeFBB(FriendlyByteBuf buf) {
        buf.writeResourceLocation(location.getResourceLocation());
        buf.writeNbt(location.toTag());
        buf.writeResourceLocation(itemLocation);
        return buf;
    }
}
