package red.felnull.otyacraftengine.client;

import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourceReloadListenerKeys;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.Nullable;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.fluid.TestFluid;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;

public class TestFabricEvent implements ClientSpriteRegistryCallback, SimpleSynchronousResourceReloadListener {
    private static final TextureAtlasSprite[] fluidSprites = {null, null};
    private static final ResourceLocation STII = new ResourceLocation(OtyacraftEngine.MODID, "block/test_block");
    private static final ResourceLocation FLOA = new ResourceLocation(OtyacraftEngine.MODID, "item/test_item");


    public static void init() {
        TestFabricEvent te = new TestFabricEvent();
        ClientSpriteRegistryCallback.event(TextureAtlas.LOCATION_BLOCKS).register(te);
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(te);

        //   TextureAtlasSprite atls = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(STII);
        //  TextureAtlasSprite atls2 = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(FLOA);

        //   fluidSprites[0] = atls;
        //   fluidSprites[1] = atls2;

        FluidRenderHandler handler = new FluidRenderHandler() {
            @Override
            public TextureAtlasSprite[] getFluidSprites(@Nullable BlockAndTintGetter blockAndTintGetter, @Nullable BlockPos blockPos, FluidState fluidState) {
                return fluidSprites;
            }
        };

        FluidRenderHandlerRegistry.INSTANCE.register(TestFluid.TEST_FLUID, handler);
        FluidRenderHandlerRegistry.INSTANCE.register(TestFluid.TEST_FLOWING_FLUID, handler);

        //   BlockRenderLayerMap.INSTANCE.putFluids(RenderType.translucent(), TestFluid.TEST_FLUID, TestFluid.TEST_FLOWING_FLUID);
    }

    @Override
    public void registerSprites(TextureAtlas atlasTexture, Registry registry) {
        registry.register(new ResourceLocation(OtyacraftEngine.MODID, "block/test_block"));
    }

    @Override
    public ResourceLocation getFabricId() {
        return new ResourceLocation(OtyacraftEngine.MODID, "frm");
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        Function<ResourceLocation, TextureAtlasSprite> atlas = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS);
        fluidSprites[0] = atlas.apply(STII);
        fluidSprites[1] = atlas.apply(FLOA);
    }

    @Override
    public Collection<ResourceLocation> getFabricDependencies() {
        return Collections.singletonList(ResourceReloadListenerKeys.TEXTURES);
    }
}
