package red.felnull.otyacraftengine.item;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class IkisugiItem extends Item implements IIkisugibleItem {
    public IkisugiItem(Properties properties) {
        super(properties);
    }

    @Override
    public ResourceLocation getRegistryName() {
        return Registry.ITEM.getKey(this);
    }

}
