package dev.felnull.otyacraftengine.client.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClientUtilInit {
    public static void init() {

    }

    public static void clear() {
        OEClientUtil.PLAYER_PROFILES.clear();
        List<UUID> ids = new ArrayList<>(OETextureUtil.NATIVE_TEXTURES.keySet());
        for (UUID id : ids) {
            OETextureUtil.freeNativeTexture(id);
        }
    }
}
