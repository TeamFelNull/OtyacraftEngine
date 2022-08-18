package dev.felnull.otyacraftenginetest.forge;

import dev.architectury.platform.forge.EventBuses;
import dev.felnull.otyacraftenginetest.OtyacraftEngineTest;
import dev.felnull.otyacraftenginetest.client.OtyacraftEngineTestClient;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(OtyacraftEngineTest.MODID)
public class OtyacraftEngineTestForge {
    public OtyacraftEngineTestForge() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        EventBuses.registerModEventBus(OtyacraftEngineTest.MODID, FMLJavaModLoadingContext.get().getModEventBus());
        OtyacraftEngineTest.init();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        OtyacraftEngineTestClient.init();
    }
}
