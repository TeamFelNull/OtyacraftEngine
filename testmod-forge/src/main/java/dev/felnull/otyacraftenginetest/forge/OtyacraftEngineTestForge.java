package dev.felnull.otyacraftenginetest.forge;

import dev.architectury.platform.forge.EventBuses;
import dev.felnull.otyacraftenginetest.OtyacraftEngineTest;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(OtyacraftEngineTest.MODID)
public class OtyacraftEngineTestForge {
    public OtyacraftEngineTestForge() {
        EventBuses.registerModEventBus(OtyacraftEngineTest.MODID, FMLJavaModLoadingContext.get().getModEventBus());
        OtyacraftEngineTest.init();
    }
}
