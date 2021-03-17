package red.felnull.otyacraftengine.mixin.fabric;

import net.minecraft.world.item.BottleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import red.felnull.otyacraftengine.fluid.IkisugiFluidTank;
import red.felnull.otyacraftengine.item.IIkisugibleFluidTankItem;

import java.util.Optional;

@Mixin(BottleItem.class)
public class BottleItemMixin implements IIkisugibleFluidTankItem {
    @Override
    public ItemStack getEmptyFluidItem() {
        return new ItemStack(Items.GLASS_BOTTLE);
    }

    @Override
    public int getCapacity() {
        return 1000 / 3;
    }

    @Override
    public Optional<IkisugiFluidTank> getFluidTank(ItemStack container) {
        IkisugiFluidTank ift = new IkisugiFluidTank(getCapacity());
        ift.setFluid(Fluids.EMPTY);
        ift.setAmount(0);
        return Optional.of(ift);
    }

    @Override
    public Optional<ItemStack> setFluidTank(ItemStack stack, IkisugiFluidTank tank) {
        if (tank.getFluid() == Fluids.WATER) {
            return Optional.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER));
        }
        return Optional.empty();
    }

    @Override
    public boolean canNotIncompleteFluidItem() {
        return true;
    }
}
