package red.felnull.otyacraftengine.mixin.fabric;

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
    public int getCapacity() {
        return 1000;
    }

    @Override
    public Optional<IkisugiFluidTank> getFluidTank(ItemStack container) {
        IkisugiFluidTank ift = new IkisugiFluidTank(getCapacity());
        ift.setFluid(content);
        ift.setAmount(content == Fluids.EMPTY ? 0 : getCapacity());
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
