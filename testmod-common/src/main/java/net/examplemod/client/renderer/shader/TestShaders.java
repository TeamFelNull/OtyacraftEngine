package net.examplemod.client.renderer.shader;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import dev.architectury.event.events.client.ClientReloadShadersEvent;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.IOException;

public class TestShaders {
    private static ShaderInstance testShader;

    public static void init() {
        ClientReloadShadersEvent.EVENT.register(TestShaders::reload);
    }

    private static void reload(ResourceManager manager, ClientReloadShadersEvent.ShadersSink sink) {
        try {
            sink.registerShader(new ShaderInstance(manager, "test", DefaultVertexFormat.NEW_ENTITY), shaderInstance -> {
                testShader = shaderInstance;
            });
        } catch (IOException e) {
            throw new RuntimeException("could not reload test shaders", e);
        }
    }

    public static ShaderInstance getTestShader() {
        return testShader;
    }
}
