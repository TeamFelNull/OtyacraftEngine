package net.examplemod.fabric.client;

import net.examplemod.client.ExampleModClient;
import net.fabricmc.api.ClientModInitializer;

public class ExampleModFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ExampleModClient.init();
    }
}
