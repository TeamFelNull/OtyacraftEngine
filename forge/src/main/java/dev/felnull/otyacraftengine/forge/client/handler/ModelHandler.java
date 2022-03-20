package dev.felnull.otyacraftengine.forge.client.handler;

import dev.felnull.otyacraftengine.client.entrypoint.OEClientEntryPointManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModelHandler {

    @SubscribeEvent
    public static void onModelRegistry(ModelRegistryEvent e) {
        OEClientEntryPointManager.getInstance().call().onModelRegistry(ForgeModelBakery::addSpecialModel);
    }

    @SubscribeEvent
    public static void onRegisterReloadListeners(RegisterClientReloadListenersEvent e) {
    /*    e.registerReloadListener(new SimplePreparableReloadListener<OEModelLoader>() {
            @Override
            protected @NotNull OEModelLoader prepare(@NotNull ResourceManager arg, @NotNull ProfilerFiller arg2) {
                return OEModelLoader.load(arg);
            }

            @Override
            protected void apply(@NotNull OEModelLoader object, @NotNull ResourceManager arg, @NotNull ProfilerFiller arg2) {
                object.apply();
            }
        });*/
    }
}
