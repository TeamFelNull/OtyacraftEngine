package red.felnull.otyacraftengine.api.register;

import me.shedaniel.architectury.platform.Platform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.api.OtyacraftEngineAPI;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class OERegistries {
    private static final Map<ResourceLocation, IRegister> REGISTRYS = new HashMap<>();

    public static void init(OtyacraftEngineAPI api) {
        OEHandlerRegister handlerRegister = new OEHandlerRegister();
        OEMODColorRegister modColorRegister = new OEMODColorRegister();

        OERegistries.setRegistry(new ResourceLocation(OtyacraftEngine.MODID, "handler"), handlerRegister);
        OERegistries.setRegistry(new ResourceLocation(OtyacraftEngine.MODID, "mod_color"), modColorRegister);

        api.integrationConsumer(n -> n.registrationHandler(handlerRegister));
        api.integrationConsumer(n -> n.registrationMODColor(modColorRegister));

        Platform.getModIds().stream().filter(n -> !modColorRegister.contains(n)).forEach(n -> {
            Random r = new Random(n.hashCode());
            modColorRegister.register(n, r.nextInt(16777215));
        });
    }

    @Environment(EnvType.CLIENT)
    public static void clientInit(OtyacraftEngineAPI api) {
        OEHandlerRegister clientHandlerRegister = new OEHandlerRegister();
        OEModelLoaderPathRegister modelLoaderPathRegister = new OEModelLoaderPathRegister();

        OERegistries.setRegistry(new ResourceLocation(OtyacraftEngine.MODID, "client_handler"), clientHandlerRegister);
        OERegistries.setRegistry(new ResourceLocation(OtyacraftEngine.MODID, "model_loader_path"), modelLoaderPathRegister);

        api.integrationConsumer(n -> n.registrationClientHandler(clientHandlerRegister));
        api.integrationConsumer(n -> n.registrationModelLoaderPath(modelLoaderPathRegister));
    }

    public static SingleRegistry<?> getSingleRegistry(ResourceLocation location) {

        return (SingleRegistry<?>) REGISTRYS.get(location);
    }

    public static DoubleRegistry<?, ?> getDoubleRegistry(ResourceLocation location) {

        return (DoubleRegistry<?, ?>) REGISTRYS.get(location);
    }

    public static void setRegistry(ResourceLocation location, IRegister registry) {
        REGISTRYS.put(location, registry);
    }

    public static boolean hasRegistryContain(ResourceLocation location) {
        return REGISTRYS.containsKey(location);
    }

}
