package red.felnull.otyacraftengine.util;

import com.mojang.authlib.GameProfile;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.SkullBlockEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class IKSGPlayerUtil {
    private static final UUID FAKE_UUID = UUID.fromString("166f3515-f173-4042-9190-ec8b14505201");
    private static final String FAKE_PLAYERNAME = "FakePlayerOfFakePlayerByFakePlayerForFakePlayer";
    private static final Map<String, GameProfile> PLAYER_PROFILES = new HashMap<>();

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
}
