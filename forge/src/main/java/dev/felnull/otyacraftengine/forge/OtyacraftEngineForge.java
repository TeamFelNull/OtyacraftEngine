package dev.felnull.otyacraftengine.forge;

import dev.architectury.platform.forge.EventBuses;
import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.client.OtyacraftEngineClient;
import dev.felnull.otyacraftengine.forge.client.handler.ClientHandlerForge;
import dev.felnull.otyacraftengine.forge.handler.CommonHandlerForge;
import dev.felnull.otyacraftengine.forge.server.handler.ServerHandlerForge;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(OtyacraftEngine.MODID)
public class OtyacraftEngineForge {
    public OtyacraftEngineForge() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        MinecraftForge.EVENT_BUS.register(CommonHandlerForge.class);
        MinecraftForge.EVENT_BUS.register(ServerHandlerForge.class);
        EventBuses.registerModEventBus(OtyacraftEngine.MODID, FMLJavaModLoadingContext.get().getModEventBus());
        OtyacraftEngine.init();
    }

    private void doClientStuff(FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(ClientHandlerForge.class);
        OtyacraftEngineClient.init();
    }
}
