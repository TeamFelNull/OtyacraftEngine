package dev.felnull.otyacraftengine.explatform.fabric;

import dev.felnull.otyacraftengine.blockentity.BlockEntityCreateSupplier;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class OERegisterExpectPlatformImpl {
    public static <T extends BlockEntity> BlockEntityType<T> createBlockEntity(BlockEntityCreateSupplier<? extends T> supplier, Block... blocks) {
        FabricBlockEntityTypeBuilder<T> builder = FabricBlockEntityTypeBuilder.create(supplier::create, blocks);
        return builder.build();
    }
}
