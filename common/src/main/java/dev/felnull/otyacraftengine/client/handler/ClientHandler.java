package dev.felnull.otyacraftengine.client.handler;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.event.events.client.ClientReloadShadersEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.client.entity.ClientPlayerInfoManager;
import dev.felnull.otyacraftengine.client.event.MoreClientLifecycleEvents;
import dev.felnull.otyacraftengine.client.event.OBJLoaderEvent;
import dev.felnull.otyacraftengine.client.renderer.shader.OEShaders;
import dev.felnull.otyacraftengine.client.renderer.texture.URLTextureManager;
import dev.felnull.otyacraftengine.entity.PlayerInfoManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.IOException;

public class ClientHandler {
    public static void init() {
        ClientLifecycleEvent.CLIENT_STOPPING.register(ClientHandler::onStopping);
        ClientLifecycleEvent.CLIENT_LEVEL_LOAD.register(ClientHandler::onLevelLoad);
        MoreClientLifecycleEvents.CLIENT_LEVEL_UNLOAD.register(ClientHandler::onLevelUnload);
        ClientTickEvent.CLIENT_POST.register(ClientHandler::ontClientTick);
        ClientReloadShadersEvent.EVENT.register(ClientHandler::onShaderReload);
        OBJLoaderEvent.LOAD_CHECK.register(ClientHandler::onObjLoadCheck);
    }

    private static EventResult onObjLoadCheck(ResourceLocation location) {
        if (OtyacraftEngine.MODID.equals(location.getNamespace()))
            return EventResult.interruptTrue();
        return EventResult.pass();
    }

    private static void onShaderReload(ResourceManager manager, ClientReloadShadersEvent.ShadersSink sink) {
        try {
            OEShaders.reload(manager, sink);
        } catch (IOException e) {
            throw new RuntimeException("could not reload test shaders", e);
        }
    }

    private static void onStopping(Minecraft minecraft) {
        URLTextureManager.getInstance().saveIndex();
    }

    private static void onLevelLoad(ClientLevel world) {
        URLTextureManager.getInstance().clear();
        PlayerInfoManager.getInstance().clear();
        ClientPlayerInfoManager.getInstance().clear();
    }

    private static void onLevelUnload(ClientLevel world) {
        URLTextureManager.getInstance().clear();
        PlayerInfoManager.getInstance().clear();
        ClientPlayerInfoManager.getInstance().clear();
    }

    private static void ontClientTick(Minecraft instance) {
        URLTextureManager.getInstance().tick();
    }
}
