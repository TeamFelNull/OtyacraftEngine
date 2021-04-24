package red.felnull.otyacraftengine.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import red.felnull.otyacraftengine.impl.OEExpectPlatform;

public class IKSGBlockEntityUtil {
    public static <T extends BlockEntityType<?>> T craeteBlockEntityType(IKSGBlockEntitySupplier<? extends BlockEntity> blockEntitySupplier, Block... blocks) {
        return (T) OEExpectPlatform.craeteBlockEntityTypeBuilder(blockEntitySupplier, blocks).build(null);
    }

    public static interface IKSGBlockEntitySupplier<T extends BlockEntity> {
        T create(BlockPos blockPos, BlockState blockState);
    }

    public static boolean hasBlockEntity(Block block) {
        return OEExpectPlatform.isBlockEntity(block);
    }
}
