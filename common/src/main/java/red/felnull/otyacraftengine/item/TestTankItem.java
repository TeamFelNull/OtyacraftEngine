package red.felnull.otyacraftengine.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import red.felnull.otyacraftengine.item.storage.ISimpleFluidTankItem;

import java.util.List;

public class TestTankItem extends Item implements ISimpleFluidTankItem {
    public TestTankItem(Properties properties) {
        super(properties);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        if (itemStack.getItem() instanceof TestTankItem) {
            ((TestTankItem) itemStack.getItem()).getFluidTank(itemStack).ifPresent(n -> {
                list.add(new TextComponent("Fluid: ").append(n.getFluidStack().getName()));
                list.add(new TextComponent("Amont: " + n.getFluidStack().getAmount().intValue() + "mb").append("/").append(n.getCapacity() + "mb"));
            });
        }

    }

    @Override
    public ItemStack getEmptyFluidTankItem() {
        return new ItemStack(this);
    }

    @Override
    public int getCapacity(ItemStack stack) {
        return 114514;
    }
}
