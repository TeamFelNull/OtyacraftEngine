package dev.felnull.otyacraftengine.client.entrypoint;

import dev.felnull.otyacraftengine.util.OEModUtil;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class OEClientEntryPointManager {
    private static final OEClientEntryPointManager INSTANCE = new OEClientEntryPointManager();
    private final List<IOEClientEntryPoint> ENTRYS = new ArrayList<>();
    private boolean inited;

    public static OEClientEntryPointManager getInstance() {
        return INSTANCE;
    }

    private synchronized void init() {
        if (inited) return;
        inited = true;
        ENTRYS.addAll(OEModUtil.getEntryPoints("otyacraftengine_client", OEClientEntryPoint.class, IOEClientEntryPoint.class));
    }

    public IOEClientEntryPoint call() {
        init();
        return new IOEClientEntryPoint() {
            @Override
            public void onModelRegistry(Consumer<ResourceLocation> register) {
                consumer(n -> n.onModelRegistry(register));
            }

            @Override
            public void onLayerRegistry(LayerRegister register) {
                consumer(n -> n.onLayerRegistry(register));
            }
        };
    }

    private void consumer(Consumer<IOEClientEntryPoint> entryPointConsumer) {
        for (IOEClientEntryPoint entry : ENTRYS) {
            entryPointConsumer.accept(entry);
        }
    }
}
