package red.felnull.otyacraftengine.item;

import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public interface IDetailedInfomationItem {

    default public void addDetailedInformation(ItemTooltipEvent e) {
        e.getToolTip().add(new TranslationTextComponent("tooltip.detailedinformation." + e.getItemStack().getItem().getTranslationKey()).func_240699_a_(TextFormatting.GRAY));
    }

}
