package red.felnull.otyacraftengine.item;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public class IkisugiBlockItem extends BlockItem implements IIkisugibleItem {
    public IkisugiBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public ResourceLocation getRegistryName() {
        return Registry.ITEM.getKey(this);
    }
}
