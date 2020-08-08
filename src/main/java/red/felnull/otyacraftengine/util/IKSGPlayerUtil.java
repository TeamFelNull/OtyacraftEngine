package red.felnull.otyacraftengine.util;

import com.mojang.authlib.GameProfile;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.SkullTileEntity;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class IKSGPlayerUtil {

    public static String FAKE_UUID = "11451419-1981-0364-364931-000000000000";
    public static String FAKE_PLAYERNAME = "UnknowOfUnknowInUnknowFromUnknow";
    protected static Map<String, GameProfile> PLAYERGAMEPROFILES = new HashMap<String, GameProfile>();

    public static String getUserName(PlayerEntity pl) {
        return pl.getGameProfile().getName();
    }

    public static String getUUID(PlayerEntity pl) {
        return PlayerEntity.getUUID(pl.getGameProfile()).toString();
    }

    public static GameProfile getPlayerProfile(String name) {
        if (PLAYERGAMEPROFILES.containsKey(name))
            return PLAYERGAMEPROFILES.get(name);

        GameProfile gp = new GameProfile(UUID.fromString(FAKE_UUID), name);
        PLAYERGAMEPROFILES.put(name, gp);

        GameProfileLoader GPL = new GameProfileLoader(name);
        GPL.start();

        return gp;
    }

    public static ServerPlayerEntity getPlayerByUUID(String uuid) {
        return IKSGServerUtil.getMinecraftServer().getPlayerList().getPlayerByUUID(UUID.fromString(uuid));
    }

    public static void grantAdvancement(ResourceLocation rl, ServerPlayerEntity spl) {

        Advancement advancement = spl.getServer().getAdvancementManager().getAdvancement(rl);
        AdvancementProgress advancementprogress = spl.getAdvancements().getProgress(advancement);

        if (advancementprogress.isDone())
            return;

        for (String s : advancementprogress.getRemaningCriteria()) {
            spl.getAdvancements().grantCriterion(advancement, s);
        }
    }

    private static class GameProfileLoader extends Thread {
        private String name;

        public GameProfileLoader(String name) {
            this.name = name;
        }

        public void run() {
            GameProfile gp = PLAYERGAMEPROFILES.get(name);
            PLAYERGAMEPROFILES.put(name, SkullTileEntity.updateGameProfile(gp));
        }
    }
}