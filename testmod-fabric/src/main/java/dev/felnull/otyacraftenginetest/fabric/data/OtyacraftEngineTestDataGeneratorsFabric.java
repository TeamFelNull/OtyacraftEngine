package dev.felnull.otyacraftenginetest.fabric.data;

import dev.felnull.otyacraftengine.fabric.data.CrossDataGeneratorAccesses;
import dev.felnull.otyacraftenginetest.data.OtyacraftEngineTestDataGenerators;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class OtyacraftEngineTestDataGeneratorsFabric implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        OtyacraftEngineTestDataGenerators.init(CrossDataGeneratorAccesses.create(fabricDataGenerator));
    }
}
