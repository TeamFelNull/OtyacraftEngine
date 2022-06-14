package dev.felnull.otyacraftengine.fabric.client.handler;

import dev.felnull.otyacraftengine.fabric.client.shape.ClientIVShapeManagerFabric;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;

public class ClientHandlerFabric {
    public static void init() {
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new ClientIVShapeManagerFabric());
    }
}
