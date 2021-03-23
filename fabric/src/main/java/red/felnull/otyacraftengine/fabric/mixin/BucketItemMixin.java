package red.felnull.otyacraftengine.fabric.mixin;

import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import red.felnull.otyacraftengine.fluid.IkisugiFluidTank;
import red.felnull.otyacraftengine.item.IIkisugibleFluidTankItem;

import java.util.Optional;

@Mixin(BucketItem.class)
public class BucketItemMixin implements IIkisugibleFluidTankItem {
    @Shadow
    @Final
    public Fluid content;

    @Override
    public ItemStack getEmptyFluidItem() {

        return new ItemStack(Items.BUCKET);
    }

    @Override
    public int getCapacity(ItemStack stack) {
        return 1000;
    }

    @Override
    public Optional<IkisugiFluidTank> getFluidTank(ItemStack stack) {
        IkisugiFluidTank ift = new IkisugiFluidTank(getCapacity(stack));
        ift.setFluid(content);
        ift.setAmount(content == Fluids.EMPTY ? 0 : getCapacity(stack));
        return Optional.of(ift);
    }

    @Override
    public Optional<ItemStack> setFluidTank(ItemStack stack, IkisugiFluidTank tank) {
        return Optional.of(new ItemStack(tank.getFluid().getBucket()));
    }

    @Override
    public boolean canNotIncompleteFluidItem() {
        return true;
    }
}
