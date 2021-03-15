package red.felnull.otyacraftengine.block;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

public abstract class IkisugiBaseEntityBlock extends BaseEntityBlock implements IIkisugibleBlock {
    protected IkisugiBaseEntityBlock(Properties properties) {
        super(properties);
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Override
    public ResourceLocation getRegistryName() {
        return Registry.BLOCK.getKey(this);
    }
}
