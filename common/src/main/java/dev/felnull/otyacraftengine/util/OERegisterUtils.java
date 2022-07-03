package dev.felnull.otyacraftengine.util;

import dev.felnull.otyacraftengine.blockentity.BlockEntityCreateSupplier;
import dev.felnull.otyacraftengine.explatform.OERegisterExpectPlatform;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;

/**
 * 登録関係のユーティリティ
 *
 * @author MORIMORI0317
 */
public class OERegisterUtils {
    @NotNull
    public static <T extends BlockEntity> BlockEntityType<T> createBlockEntity(@NotNull BlockEntityCreateSupplier<? extends T> supplier, Block... blocks) {
        return OERegisterExpectPlatform.createBlockEntity(supplier, blocks);
    }
}
