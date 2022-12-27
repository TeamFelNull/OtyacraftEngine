package dev.felnull.otyacraftengine.client.handler;

import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.event.events.client.ClientReloadShadersEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.felnull.otyacraftengine.OEConfig;
import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.client.entity.ClientPlayerInfoManager;
import dev.felnull.otyacraftengine.client.event.MoreClientLifecycleEvents;
import dev.felnull.otyacraftengine.client.renderer.shader.OEShaders;
import dev.felnull.otyacraftengine.client.renderer.texture.URLTextureManager;
import dev.felnull.otyacraftengine.entity.PlayerInfoManager;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraft.world.InteractionResult;

import java.io.IOException;

public class ClientHandler {
    public static void init() {
        ClientLifecycleEvent.CLIENT_STOPPING.register(ClientHandler::onStopping);
        ClientLifecycleEvent.CLIENT_LEVEL_LOAD.register(ClientHandler::onLevelLoad);
        MoreClientLifecycleEvents.CLIENT_LEVEL_UNLOAD.register(ClientHandler::onLevelUnload);
        ClientTickEvent.CLIENT_POST.register(ClientHandler::ontClientTick);
        ClientReloadShadersEvent.EVENT.register(ClientHandler::onShaderReload);
        AutoConfig.getConfigHolder((Class<OEConfig>) OtyacraftEngine.getConfig().getClass()).registerSaveListener(ClientHandler::onConfigSave);
    }

    private static void onShaderReload(ResourceProvider provider, ClientReloadShadersEvent.ShadersSink sink) {
        try {
            OEShaders.reload(provider, sink);
        } catch (IOException e) {
            throw new RuntimeException("could not reload test shaders", e);
        }
    }

    private static void onStopping(Minecraft minecraft) {
        URLTextureManager.getInstance().save();
    }

    private static void onLevelLoad(ClientLevel world) {
        URLTextureManager.getInstance().release();
        PlayerInfoManager.getInstance().clear();
        ClientPlayerInfoManager.getInstance().clear();
    }

    private static void onLevelUnload(ClientLevel world) {
        URLTextureManager.getInstance().release();
        PlayerInfoManager.getInstance().clear();
        ClientPlayerInfoManager.getInstance().clear();
    }

    private static void ontClientTick(Minecraft instance) {
        URLTextureManager.getInstance().tick();
    }

    private static InteractionResult onConfigSave(ConfigHolder<OEConfig> configHolder, OEConfig config) {
        URLTextureManager.getInstance().release();
        return InteractionResult.PASS;
    }
}
