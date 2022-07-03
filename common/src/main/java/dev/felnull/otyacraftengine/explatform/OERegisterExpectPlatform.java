package dev.felnull.otyacraftengine.explatform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.felnull.otyacraftengine.blockentity.BlockEntityCreateSupplier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class OERegisterExpectPlatform {
    @ExpectPlatform
    public static <T extends BlockEntity> BlockEntityType<T> createBlockEntity(BlockEntityCreateSupplier<? extends T> supplier, Block... blocks) {
        throw new AssertionError();
    }
}
