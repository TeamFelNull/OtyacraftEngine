package dev.felnull.otyacraftengine.fabric.client.handler;

import dev.felnull.otyacraftengine.fabric.client.motion.MotionManagerFabric;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;

public class ResourceReloadHandler {

    public static void init() {
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new MotionManagerFabric());
    }
}
