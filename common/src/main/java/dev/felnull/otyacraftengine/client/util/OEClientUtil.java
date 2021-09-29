package dev.felnull.otyacraftengine.client.util;

import com.mojang.authlib.GameProfile;
import net.minecraft.world.level.block.entity.SkullBlockEntity;

import java.util.HashMap;
import java.util.Map;

public class OEClientUtil {
    protected static final Map<String, GameProfile> PLAYER_PROFILES = new HashMap<>();

    /**
     * クライアントでプレイヤープロフィールを取得する
     *
     * @param name プレイヤー名
     * @return プロフィール
     */
    public static GameProfile getClientPlayerProfile(String name) {
        if (PLAYER_PROFILES.containsKey(name))
            return PLAYER_PROFILES.get(name);

        GameProfile gp = new GameProfile(null, name);
        PLAYER_PROFILES.put(name, gp);
        SkullBlockEntity.updateGameprofile(gp, n -> PLAYER_PROFILES.put(name, n));
        return gp;
    }
}
