package dev.felnull.otyacraftengine.util;

import com.google.gson.JsonObject;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.chunk.LevelChunk;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class OEPlayerUtil {
    private static final String UUID_PLAYER_URL = "https://sessionserver.mojang.com/session/minecraft/profile/%s";

    public static Optional<String> getNameByUUID(UUID uuid) {
        try {
            JsonObject jo = OEURLUtil.getJson(new URL(String.format(UUID_PLAYER_URL, uuid.toString())));
            String name = jo.get("name").getAsString();
            return Optional.ofNullable(name);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }

    public static CompletableFuture<Void> getNameByUUIDAsync(UUID id, Consumer<Optional<String>> name) {
        try {
            return OEURLUtil.getJsonAsync(new URL(String.format(UUID_PLAYER_URL, id.toString())), n -> {
                String na = null;
                try {
                    na = n.get("name").getAsString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                name.accept(Optional.ofNullable(na));
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(null);
    }

    public static void giveItem(Player player, ItemStack stack) {
        if (!player.addItem(stack))
            player.drop(stack, false, true);
    }

    public static void changeOrGiveItem(Player player, InteractionHand hand, ItemStack stack) {
        ItemStack heldItem = player.getItemInHand(hand);
        if (!player.getAbilities().instabuild)
            heldItem.shrink(1);
        if (heldItem.isEmpty()) {
            player.setItemInHand(hand, stack);
        } else {
            giveItem(player, stack);
        }
    }

    public static void doPlayers(LevelChunk chunk, Consumer<ServerPlayer> playerConsumer) {
        ((ServerChunkCache) chunk.getLevel().getChunkSource()).chunkMap.getPlayers(chunk.getPos(), false).forEach(playerConsumer);
    }

}
