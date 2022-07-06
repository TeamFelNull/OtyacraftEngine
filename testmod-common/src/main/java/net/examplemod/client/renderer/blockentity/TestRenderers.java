package net.examplemod.client.renderer.blockentity;

import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import net.examplemod.blockentity.TestBlockEntitys;

public class TestRenderers {
    public static void init() {
        BlockEntityRendererRegistry.register(TestBlockEntitys.TEST_ROTED_BLOCKENTITY.get(), TestRotedRenderer::new);
    }
}
