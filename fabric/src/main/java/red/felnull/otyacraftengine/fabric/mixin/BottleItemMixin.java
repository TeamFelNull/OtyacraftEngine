package red.felnull.otyacraftengine.fabric.mixin;

import net.minecraft.world.item.BottleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import red.felnull.otyacraftengine.fluid.IkisugiFluidTank;
import red.felnull.otyacraftengine.item.IIkisugibleNotIncompleteFluidTankItem;

import java.util.Optional;

@Mixin(BottleItem.class)
public class BottleItemMixin implements IIkisugibleNotIncompleteFluidTankItem {

    @Override
    public ItemStack getEmptyFluidItem() {
        return new ItemStack(Items.GLASS_BOTTLE);
    }

    @Override
    public int getPriorityCapacity(ItemStack stack) {
        return 1000 / 3;
    }

    @Override
    public Optional<IkisugiFluidTank> getPriorityFluidTank(ItemStack stack) {
        return Optional.of(IkisugiFluidTank.createEmpty());
    }

    @Override
    public Optional<ItemStack> setPriorityFluidTank(ItemStack stack, IkisugiFluidTank tank) {
        if (tank.getFluid() == Fluids.WATER) {
            return Optional.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER));
        }
        return Optional.empty();
    }
}
