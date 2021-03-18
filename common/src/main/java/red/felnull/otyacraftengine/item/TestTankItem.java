package red.felnull.otyacraftengine.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import red.felnull.otyacraftengine.fluid.IkisugiFluidTank;

import java.util.List;
import java.util.Optional;

public class TestTankItem extends Item implements IIkisugibleFluidTankItem {
    public TestTankItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack getEmptyFluidItem() {
        return new ItemStack(this);
    }

    @Override
    public int getCapacity(ItemStack stack) {
        return 19419;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        Optional<IkisugiFluidTank> tank = getFluidTank(itemStack);
        tank.ifPresent(n -> {
            list.add(new TextComponent("Fluid: ").append(n.getFluidStack().getName()));
            list.add(new TextComponent("Amont: " + n.getFluidStack().getAmount().intValue() + "mb").append("/").append(n.getCapacity() + "mb"));
        });
    }
}
