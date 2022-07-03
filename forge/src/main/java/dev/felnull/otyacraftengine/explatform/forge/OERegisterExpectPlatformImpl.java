package dev.felnull.otyacraftengine.explatform.forge;

import dev.felnull.otyacraftengine.blockentity.BlockEntityCreateSupplier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class OERegisterExpectPlatformImpl {
    public static <T extends BlockEntity> BlockEntityType<T> createBlockEntity(BlockEntityCreateSupplier<? extends T> supplier, Block... blocks) {
        BlockEntityType.Builder<T> builder = BlockEntityType.Builder.of(supplier::create, blocks);
        return builder.build(null);
    }
}
