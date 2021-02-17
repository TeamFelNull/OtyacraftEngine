package red.felnull.otyacraftengine.api.register;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.api.OtyacraftEngineAPI;

import java.util.HashMap;
import java.util.Map;

public class OERegistries {
    private static final Map<ResourceLocation, SingleRegistry<?>> SINGLE_REGISTRYS = new HashMap<>();

    public static void init(OtyacraftEngineAPI api) {
        OEHandlerRegister handlerRegister = new OEHandlerRegister();

        OERegistries.setSingleRegistry(new ResourceLocation(OtyacraftEngine.MODID, "handler"), handlerRegister);

        api.integrationConsumer(n -> n.registrationHandler(handlerRegister));
    }

    @Environment(EnvType.CLIENT)
    public static void clientInit(OtyacraftEngineAPI api) {
        OEHandlerRegister clientHandlerRegister = new OEHandlerRegister();

        OERegistries.setSingleRegistry(new ResourceLocation(OtyacraftEngine.MODID, "client_handler"), clientHandlerRegister);

        api.integrationConsumer(n -> n.registrationClientHandler(clientHandlerRegister));
    }

    public static SingleRegistry<?> getSingleRegistry(ResourceLocation location) {

        return SINGLE_REGISTRYS.get(location);
    }

    public static void setSingleRegistry(ResourceLocation location, SingleRegistry<?> registry) {
        SINGLE_REGISTRYS.put(location, registry);
    }

    public static boolean hasSingleRegistryContain(ResourceLocation location) {
        return SINGLE_REGISTRYS.containsKey(location);
    }

}
