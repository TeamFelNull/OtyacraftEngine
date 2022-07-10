package dev.felnull.otyacraftengine.forge.client.handler;

import dev.felnull.otyacraftengine.client.callpoint.ClientCallPointManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModelHandler {

    @SubscribeEvent
    public static void onModelRegistry(ModelRegistryEvent e) {
        ClientCallPointManager.getInstance().call().onModelRegistry(ForgeModelBakery::addSpecialModel);
    }
}
