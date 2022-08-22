package dev.felnull.otyacraftengine.networking.existence;

import dev.felnull.otyacraftengine.item.IInstructionItem;
import dev.felnull.otyacraftengine.item.location.PlayerItemLocation;
import dev.felnull.otyacraftengine.item.location.PlayerItemLocations;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public record ItemExistence(PlayerItemLocation location, ResourceLocation itemRegistryName) {
    public static ItemExistence read(FriendlyByteBuf buf) {
        return new ItemExistence(PlayerItemLocations.loadFromTag(buf.readNbt()), buf.readResourceLocation());
    }

    public static ItemExistence getByItemLocation(ItemStack stack, PlayerItemLocation location) {
        var itm = Registry.ITEM.getKey(stack.getItem());
        return new ItemExistence(location, itm);
    }

    public boolean check(Player player) {
        if (location == null) return false;
        var litm = location.getItem(player);
        if (litm.isEmpty()) return false;
        var itm = Registry.ITEM.get(itemRegistryName);
        return litm.getItem() == itm && itm instanceof IInstructionItem;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeNbt(PlayerItemLocations.saveToTag(location));
        buf.writeResourceLocation(itemRegistryName);
    }
}
