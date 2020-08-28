package red.felnull.otyacraftengine;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import red.felnull.otyacraftengine.client.config.ClientConfig;
import red.felnull.otyacraftengine.proxy.ClientProxy;
import red.felnull.otyacraftengine.proxy.CommonProxy;

@Mod(OtyacraftEngine.MODID)
public class OtyacraftEngine {
    public static final String MODID = "otyacraftengine";
    public static final Logger LOGGER = LogManager.getLogger();

    @SuppressWarnings("deprecation")
    public static final CommonProxy proxy = DistExecutor
            .runForDist(() -> () -> new ClientProxy(), () -> () -> new CommonProxy());

    public OtyacraftEngine() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        ClientConfig.init();
    }

    private void setup(final FMLCommonSetupEvent event) {
        proxy.preInit();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        ClientProxy.clientInit();
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        proxy.init();
    }

    private void processIMC(final InterModProcessEvent event) {
        proxy.posInit();
    }

}
