package red.felnull.otyacraftengine.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.SkullTileEntity;
import net.minecraft.util.ResourceLocation;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.StreamSupport;

public class IKSGPlayerUtil {
    public static final String FAKE_UUID = "11451419-1981-0364-364931-000000000000";
    public static final String FAKE_PLAYERNAME = "UnknowOfUnknowInUnknowFromUnknow";
    protected static final Map<String, GameProfile> PLAYERGAMEPROFILES = new HashMap<>();
    protected static final Map<String, String> PLAYERSKINURLS = new HashMap<>();
    private static final Gson gson = new Gson();

    public static String getUserName(PlayerEntity pl) {
        return pl.getGameProfile().getName();
    }

    public static String getUUID(PlayerEntity pl) {
        return PlayerEntity.getUUID(pl.getGameProfile()).toString();
    }

    public static String getPlayerSkinTextureURL(String uuid) {
        if (PLAYERSKINURLS.containsKey(uuid))
            return PLAYERSKINURLS.get(uuid);

        PLAYERSKINURLS.put(uuid, "");

        SkinURLLoader SUL = new SkinURLLoader(uuid);
        SUL.start();

        return "";
    }

    public static GameProfile getPlayerProfileByUUID(String uuid) {
        if (PLAYERGAMEPROFILES.containsKey(uuid))
            return PLAYERGAMEPROFILES.get(uuid);

        GameProfile gp = new GameProfile(UUID.fromString(uuid), null);
        PLAYERGAMEPROFILES.put(uuid, gp);

        GameProfileLoader GPL = new GameProfileLoader(uuid);
        GPL.start();

        return gp;
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

    private static class SkinURLLoader extends Thread {
        private String uuid;

        public SkinURLLoader(String uuid) {
            this.uuid = uuid;
        }

        public void run() {
            try {
                String f1 = IKSGURLUtil.getURLResponse("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid);
                JsonObject f1jo = gson.fromJson(f1, JsonObject.class);
                Optional<String> b64d = StreamSupport.stream(f1jo.getAsJsonArray("properties").spliterator(), false).filter(n -> {
                    JsonObject jo = n.getAsJsonObject();
                    return !jo.get("name").isJsonNull() && jo.get("name").getAsString().equals("textures");
                }).map(n -> n.getAsJsonObject().get("value").getAsString()).findAny();

                if (!b64d.isPresent())
                    PLAYERSKINURLS.put(uuid, "");

                byte[] b = Base64.getUrlDecoder().decode(b64d.get());
                String de1 = new String(b, StandardCharsets.UTF_8);

                JsonObject f2jo = gson.fromJson(de1, JsonObject.class);

                PLAYERSKINURLS.put(uuid, f2jo.getAsJsonObject("textures").getAsJsonObject("SKIN").get("url").getAsString());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
