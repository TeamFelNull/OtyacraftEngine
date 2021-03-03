package red.felnull.otyacraftengine;

import net.fabricmc.api.ModInitializer;
import net.minecraft.world.item.BucketItem;

public class OtyacraftEngineFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        OtyacraftEngine.init();
    }
}
