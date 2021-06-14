package red.felnull.otyacraftengine.util;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.SkullBlockEntity;

import java.net.URL;
import java.util.*;

public class IKSGPlayerUtil {
    private static final UUID FAKE_UUID = UUID.fromString("166f3515-f173-4042-9190-ec8b14505201");
    private static final String FAKE_PLAYERNAME = "FakePlayerOfFakePlayerByFakePlayerForFakePlayer";
    private static final Map<String, GameProfile> PLAYER_PROFILES = new HashMap<>();
    private static final Map<UUID, String> UUID_PLAYERNAMES = new HashMap<>();
    private static final List<UUID> LOADING_UUIDNAME = new ArrayList<>();

    public static List<ServerPlayer> getOnlinePlayers() {
        return IKSGServerUtil.getMinecraftServer().getPlayerList().getPlayers();
    }

    public static boolean isOnlinePlayer(UUID uuid) {
        return getOnlinePlayers().stream().anyMatch(n -> n.getGameProfile().getId().equals(uuid));
    }

    public static void grantAdvancement(ResourceLocation rl, ServerPlayer spl) {

        Advancement advancement = spl.getServer().getAdvancements().getAdvancement(rl);
        AdvancementProgress advancementprogress = spl.getAdvancements().getOrStartProgress(advancement);

        if (advancementprogress.isDone())
            return;

        for (String s : advancementprogress.getRemainingCriteria()) {
            spl.getAdvancements().award(advancement, s);
        }
    }

    public static UUID getFakeUUID() {
        return FAKE_UUID;
    }

    public static String getFakePlayerName() {
        return FAKE_PLAYERNAME;
    }

    public static GameProfile getPlayerProfile(String name) {
        if (PLAYER_PROFILES.containsKey(name))
            return PLAYER_PROFILES.get(name);

        GameProfile gp = new GameProfile(null, name);
        PLAYER_PROFILES.put(name, gp);

        SkullBlockEntity.updateGameprofile(gp, n -> PLAYER_PROFILES.put(name, n));

        return gp;
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

    public static String getNameByUUID(UUID uuid) {
        if (UUID_PLAYERNAMES.containsKey(uuid))
            return UUID_PLAYERNAMES.get(uuid);
        try {
            String url = "https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString();
            JsonObject jo = IKSGURLUtil.getJsonResponse(new URL(url));
            String name = jo.get("name").getAsString();
            UUID_PLAYERNAMES.put(uuid, name);
            return name;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        UUID_PLAYERNAMES.put(uuid, FAKE_PLAYERNAME);
        return FAKE_PLAYERNAME;
    }

    public static String getNameByUUIDNoSync(UUID uuid) {
        if (UUID_PLAYERNAMES.containsKey(uuid))
            return UUID_PLAYERNAMES.get(uuid);

        if (!LOADING_UUIDNAME.contains(uuid)) {
            LOADING_UUIDNAME.add(uuid);
            NameByUUIDLoadThread nbult = new NameByUUIDLoadThread(uuid);
            nbult.start();
        }

        return FAKE_PLAYERNAME;
    }

    private static class NameByUUIDLoadThread extends Thread {
        private final UUID uuid;

        public NameByUUIDLoadThread(UUID uuid) {
            this.uuid = uuid;
        }

        @Override
        public void run() {
            getNameByUUID(uuid);
            LOADING_UUIDNAME.remove(uuid);
        }
    }
}
