package red.felnull.otyacraftengine.client.fabric.handler;

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
import red.felnull.otyacraftengine.fluid.IkisugiFluid;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class RenderHandler implements ClientSpriteRegistryCallback, SimpleSynchronousResourceReloadListener, FluidRenderHandler {
    private static final Map<IkisugiFluid, TextureAtlasSprite[]> fluidSprites = new HashMap<>();

    public static void init() {
        RenderHandler handler = new RenderHandler();
        net.minecraft.core.Registry.FLUID.stream().filter(n -> n instanceof IkisugiFluid).forEach(n -> fluidSprites.put((IkisugiFluid) n, null));
        ClientSpriteRegistryCallback.event(TextureAtlas.LOCATION_BLOCKS).register(handler);
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(handler);
        fluidSprites.forEach((n, m) -> FluidRenderHandlerRegistry.INSTANCE.register(n, handler));
    }

    @Override
    public void registerSprites(TextureAtlas atlasTexture, Registry registry) {
        fluidSprites.forEach((n, m) -> {
            registry.register(n.getProperties().getStillTexture());
            registry.register(n.getProperties().getFlowingTexture());
            registry.register(n.getProperties().getOverlayTexture());
        });
    }

    @Override
    public ResourceLocation getFabricId() {
        return new ResourceLocation(OtyacraftEngine.MODID, "resource_reloader");
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        Map<IkisugiFluid, TextureAtlasSprite[]> reloadedSprotes = new HashMap<>();
        Function<ResourceLocation, TextureAtlasSprite> atlas = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS);
        fluidSprites.forEach((n, m) -> {
            TextureAtlasSprite[] fluidSprites = {null, null, null};
            fluidSprites[0] = atlas.apply(n.getProperties().getStillTexture());
            fluidSprites[1] = atlas.apply(n.getProperties().getFlowingTexture());
            fluidSprites[2] = atlas.apply(n.getProperties().getOverlayTexture());
            reloadedSprotes.put(n, fluidSprites);
        });
        fluidSprites.clear();
        fluidSprites.putAll(reloadedSprotes);
    }

    @Override
    public Collection<ResourceLocation> getFabricDependencies() {
        return Collections.singletonList(ResourceReloadListenerKeys.TEXTURES);
    }

    @Override
    public TextureAtlasSprite[] getFluidSprites(@Nullable BlockAndTintGetter blockAndTintGetter, @Nullable BlockPos blockPos, FluidState fluidState) {
        if (fluidState.getType() instanceof IkisugiFluid)
            return fluidSprites.get(fluidState.getType());

        return new TextureAtlasSprite[]{null, null, null};
    }

    @Override
    public int getFluidColor(@Nullable BlockAndTintGetter view, @Nullable BlockPos pos, FluidState state) {
        return ((IkisugiFluid) state.getType()).getProperties().getWorldColor(view, pos);
    }
}
