package net.examplemod.forge;

import dev.architectury.platform.forge.EventBuses;
import net.examplemod.ExampleMod;
import net.examplemod.client.ExampleModClient;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ExampleMod.MODID)
public class ExampleModForge {
    public ExampleModForge() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        EventBuses.registerModEventBus(ExampleMod.MODID, FMLJavaModLoadingContext.get().getModEventBus());
        ExampleMod.init();
    }

    private void doClientStuff(FMLClientSetupEvent event) {
        ExampleModClient.init();
    }
}
