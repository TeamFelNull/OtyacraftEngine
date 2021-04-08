package red.felnull.otyacraftengine.fabric.mixin;

import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import red.felnull.otyacraftengine.fluid.storage.FluidTank;
import red.felnull.otyacraftengine.item.IIkisugibleNotIncompleteFluidTankItem;

import java.util.Optional;

@Mixin(BucketItem.class)
public class BucketItemMixin implements IIkisugibleNotIncompleteFluidTankItem {
    @Shadow
    @Final
    public Fluid content;

    @Override
    public ItemStack getEmptyFluidItem() {

        return new ItemStack(Items.BUCKET);
    }

    @Override
    public int getPriorityCapacity(ItemStack stack) {
        return 1000;
    }

    @Override
    public Optional<FluidTank> getPriorityFluidTank(ItemStack stack) {
        FluidTank ift = new FluidTank(getPriorityCapacity(stack));
        ift.setFluid(content);
        ift.setAmount(content == Fluids.EMPTY ? 0 : getPriorityCapacity(stack));
        return Optional.of(ift);
    }

    @Override
    public Optional<ItemStack> setPriorityFluidTank(ItemStack stack, FluidTank tank) {
        return Optional.of(new ItemStack(tank.getFluid().getBucket()));
    }
}
