package red.felnull.otyacraftengine.block;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class IkisugiBlock extends Block implements IIkisugibleBlock {
    public IkisugiBlock(Properties properties) {
        super(properties);
    }

    @Override
    public ResourceLocation getRegistryName() {
        return Registry.BLOCK.getKey(this);
    }
}
