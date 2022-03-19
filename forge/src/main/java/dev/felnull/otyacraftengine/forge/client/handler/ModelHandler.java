package dev.felnull.otyacraftengine.forge.client.handler;

import dev.felnull.otyacraftengine.client.entrypoint.OEClientEntryPointManager;
import dev.felnull.otyacraftengine.client.model.OEModelLoader;
import dev.felnull.otyacraftengine.client.model.obj.OEOBJLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModelHandler {

    @SubscribeEvent
    public static void onModelRegistry(ModelRegistryEvent e) {
        OEClientEntryPointManager.getInstance().call().onModelRegistry(ForgeModelBakery::addSpecialModel);
    }

    @SubscribeEvent
    public static void onRegisterReloadListeners(RegisterClientReloadListenersEvent e) {
        e.registerReloadListener(new SimplePreparableReloadListener<OEModelLoader>() {
            @Override
            protected @NotNull OEModelLoader prepare(@NotNull ResourceManager arg, @NotNull ProfilerFiller arg2) {
                return OEModelLoader.load(arg);
            }

            @Override
            protected void apply(@NotNull OEModelLoader object, @NotNull ResourceManager arg, @NotNull ProfilerFiller arg2) {
                object.apply();
            }
        });
    }

    @SubscribeEvent
    public static void onModelBake(ModelBakeEvent e) {
        OEModelLoader.onBake(e.getModelLoader());
    }

    @SubscribeEvent
    public static void onTextureStith(TextureStitchEvent.Pre e) {
        for (ResourceLocation tex : OEOBJLoader.getInstance().getLoadTextures(e.getAtlas())) {
            e.addSprite(tex);
        }
    }
}
