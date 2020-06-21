package red.felnull.otyacraftengine.block;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILightReader;

import javax.annotation.Nullable;

public interface IColorbleBlock {
    int getColoer(BlockState state, @Nullable ILightReader ligh, @Nullable BlockPos pos, int layer);
}
