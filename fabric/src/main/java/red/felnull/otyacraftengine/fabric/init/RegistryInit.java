package red.felnull.otyacraftengine.fabric.init;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import red.felnull.otyacraftengine.api.OtyacraftEngineAPI;
import red.felnull.otyacraftengine.api.helper.BiomeRegisterHelper;
import red.felnull.otyacraftengine.util.IKSGBiomeUtil;

public class RegistryInit {
    private static final OtyacraftEngineAPI api = OtyacraftEngineAPI.getInstance();

    public static void init() {
        api.integrationConsumer(n -> {
            BiomeRegisterHelper brh = new BiomeRegisterHelper();
            n.biomeRegisterHelper(brh);
            brh.getRegistrationBiomes().forEach((n2, m) -> Registry.register(BuiltinRegistries.BIOME, n2, m));
            brh.getAddOverworldContinentalBiome().forEach((n2, m) -> IKSGBiomeUtil.addOverworldContinentalBiome(n2, m.type, m.weight));
        });
    }
}
