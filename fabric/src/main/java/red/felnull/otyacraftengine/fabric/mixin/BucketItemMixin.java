package red.felnull.otyacraftengine.fabric.mixin;

import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import red.felnull.otyacraftengine.fluid.storage.FluidTank;
import red.felnull.otyacraftengine.item.storage.INotIncompleteFluidTankItem;

import java.util.Optional;

@Mixin(BucketItem.class)
public class BucketItemMixin implements INotIncompleteFluidTankItem {
    @Shadow
    @Final
    public Fluid content;

    @Override
    public ItemStack getEmptyFluidTankItem() {
        return new ItemStack(Items.BUCKET);
    }

    @Override
    public Optional<ItemStack> setFluidTank(ItemStack stack, FluidTank tank) {
        return Optional.of(new ItemStack(tank.getFluid().getBucket()));
    }

    @Override
    public int getCapacity(ItemStack stack) {
        return 1000;
    }

    @Override
    public Optional<Fluid> getFluid(ItemStack stack) {
        return Optional.of(content);
    }

    @Override
    public boolean isFluidTankItem(ItemStack stack) {
        return !((Object) this instanceof MobBucketItem);
    }
}
