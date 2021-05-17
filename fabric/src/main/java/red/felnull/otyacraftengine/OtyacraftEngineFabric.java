package red.felnull.otyacraftengine;

import me.shedaniel.architectury.event.events.ChatEvent;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.resources.language.I18n;
import red.felnull.otyacraftengine.fabric.init.RegistryInit;

public class OtyacraftEngineFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        OtyacraftEngine.init();
        RegistryInit.init();
    }
}
