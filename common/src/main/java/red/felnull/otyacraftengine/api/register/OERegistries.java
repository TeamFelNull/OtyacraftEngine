package red.felnull.otyacraftengine.api.register;

import me.shedaniel.architectury.platform.Platform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import red.felnull.otyacraftengine.api.OtyacraftEngineAPI;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class OERegistries {
    private static final Map<Class<? extends IRegister>, IRegister> REGISTRYS = new HashMap<>();

    public static void init(OtyacraftEngineAPI api) {
        OEHandlerRegister handlerRegister = new OEHandlerRegister();
        OEMODColorRegister modColorRegister = new OEMODColorRegister();

        OERegistries.setRegistry(OEHandlerRegister.class, handlerRegister);
        OERegistries.setRegistry(OEMODColorRegister.class, modColorRegister);

        api.integrationConsumer(n -> {
            n.registrationHandler(handlerRegister);
            n.registrationMODColor(modColorRegister);
        });

        Platform.getModIds().stream().filter(n -> !modColorRegister.contains(n)).forEach(n -> {
            Random r = new Random(n.hashCode());
            modColorRegister.register(n, r.nextInt(16777215));
        });
    }

    @Environment(EnvType.CLIENT)
    public static void clientInit(OtyacraftEngineAPI api) {
        OEClientHandlerRegister clientHandlerRegister = new OEClientHandlerRegister();
        OEModelLoaderPathRegister modelLoaderPathRegister = new OEModelLoaderPathRegister();

        OERegistries.setRegistry(OEClientHandlerRegister.class, clientHandlerRegister);
        OERegistries.setRegistry(OEModelLoaderPathRegister.class, modelLoaderPathRegister);

        api.integrationConsumer(n -> {
            n.registrationClientHandler(clientHandlerRegister);
            n.registrationModelLoaderPath(modelLoaderPathRegister);
        });
    }

    public static <T extends IRegister> T getRegistry(Class<T> register) {
        return (T) REGISTRYS.get(register);
    }

    public static <T extends IRegister> void setRegistry(Class<T> register, T registry) {
        REGISTRYS.put(register, registry);
    }

    public static <T extends IRegister> boolean hasRegistryContain(Class<T> register) {
        return REGISTRYS.containsKey(register);
    }

}
