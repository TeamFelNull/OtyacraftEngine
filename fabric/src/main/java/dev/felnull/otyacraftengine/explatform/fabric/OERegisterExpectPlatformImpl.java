package dev.felnull.otyacraftengine.explatform.fabric;

import dev.architectury.registry.registries.RegistrySupplier;
import dev.felnull.otyacraftengine.blockentity.BlockEntityCreateSupplier;
import dev.felnull.otyacraftengine.fabric.mixin.PoiTypesInvoker;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class OERegisterExpectPlatformImpl {
    public static <T extends BlockEntity> BlockEntityType<T> createBlockEntity(BlockEntityCreateSupplier<? extends T> supplier, Block... blocks) {
        FabricBlockEntityTypeBuilder<T> builder = FabricBlockEntityTypeBuilder.create(supplier::create, blocks);
        return builder.build();
    }

    public static void registerPoiTypeBlockStates(RegistrySupplier<PoiType> poiTypeRegistrySupplier) {
        var rk = BuiltInRegistries.POINT_OF_INTEREST_TYPE.getResourceKey(poiTypeRegistrySupplier.get()).orElseThrow();
        PoiTypesInvoker.invokeRegisterBlockStates(BuiltInRegistries.POINT_OF_INTEREST_TYPE.getHolderOrThrow(rk), poiTypeRegistrySupplier.get().matchingStates());
    }
}
