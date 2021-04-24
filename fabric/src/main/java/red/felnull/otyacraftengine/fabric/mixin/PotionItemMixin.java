package red.felnull.otyacraftengine.fabric.mixin;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.ThrowablePotionItem;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import red.felnull.otyacraftengine.fluid.storage.FluidTank;
import red.felnull.otyacraftengine.item.storage.INotIncompleteFluidTankItem;

import java.util.Optional;

@Mixin(PotionItem.class)
public class PotionItemMixin implements INotIncompleteFluidTankItem {

    @Override
    public ItemStack getEmptyFluidTankItem() {
        return new ItemStack(Items.GLASS_BOTTLE);
    }

    @Override
    public Optional<ItemStack> setFluidTank(ItemStack stack, FluidTank tank) {
        if (tank.getFluid() == Fluids.WATER) {
            return Optional.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER));
        }
        return Optional.empty();
    }

    @Override
    public int getCapacity(ItemStack stack) {
        return 1000 / 3;
    }

    @Override
    public Optional<Fluid> getFluid(ItemStack stack) {
        if (PotionUtils.getPotion(stack) == Potions.WATER) {
            return Optional.of(Fluids.WATER);
        }
        return Optional.empty();
    }

    @Override
    public boolean isFluidTankItem(ItemStack stack) {
        return PotionUtils.getPotion(stack) == Potions.WATER && !(stack.getItem() instanceof ThrowablePotionItem);
    }
}
