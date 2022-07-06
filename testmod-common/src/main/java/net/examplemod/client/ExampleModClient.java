package net.examplemod.client;

import net.examplemod.client.handler.ClientHandler;
import net.examplemod.client.renderer.blockentity.TestRenderers;
import net.examplemod.client.renderer.shader.TestShaders;

public class ExampleModClient {
    public static void init() {
        ClientHandler.init();
        TestRenderers.init();
        TestShaders.init();
    }
}
