package dev.felnull.otyacraftengine.client.util;

import dev.felnull.otyacraftengine.OtyacraftEngine;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClientUtilInit {
    private static final Minecraft mc = Minecraft.getInstance();

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
