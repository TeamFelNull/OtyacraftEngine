package red.felnull.otyacraftengine.block;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;

import javax.annotation.Nullable;

public interface IColorbleBlock {
    int getColoer(BlockState state, @Nullable IBlockDisplayReader ligh, @Nullable BlockPos pos, int layer);
}
