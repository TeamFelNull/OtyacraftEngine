package red.felnull.otyacraftengine.item;

import net.minecraft.item.HorseArmorItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class HorseArmorItemByIKSG extends HorseArmorItem {
    public HorseArmorItemByIKSG(int armor, Properties properties) {
        super(armor, (ResourceLocation) null, properties);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ResourceLocation func_219976_d() {
        return new ResourceLocation(this.getRegistryName().getNamespace(), "textures/models/horse_armor/" + this.getRegistryName().getPath() + ".png");
    }

}
