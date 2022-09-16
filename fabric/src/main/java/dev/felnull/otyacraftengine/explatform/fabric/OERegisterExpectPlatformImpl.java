package dev.felnull.otyacraftengine.explatform.fabric;

import dev.architectury.registry.registries.RegistrySupplier;
import dev.felnull.otyacraftengine.blockentity.BlockEntityCreateSupplier;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class OERegisterExpectPlatformImpl {
    public static <T extends BlockEntity> BlockEntityType<T> createBlockEntity(BlockEntityCreateSupplier<? extends T> supplier, Block... blocks) {
        FabricBlockEntityTypeBuilder<T> builder = FabricBlockEntityTypeBuilder.create(supplier::create, blocks);
        return builder.build();
    }

    public static void registerPoiTypeBlockStates(RegistrySupplier<PoiType> poiTypeRegistrySupplier) {
        var rk = Registry.POINT_OF_INTEREST_TYPE.getResourceKey(poiTypeRegistrySupplier.get()).orElseThrow();
        PoiTypes.registerBlockStates(Registry.POINT_OF_INTEREST_TYPE.getHolderOrThrow(rk));
        PoiTypes.ALL_STATES.addAll(poiTypeRegistrySupplier.get().matchingStates());
    }
}
