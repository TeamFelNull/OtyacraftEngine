package red.felnull.otyacraftengine.item;

import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public interface IDetailedInfomationItem {

    default public void addDetailedInformation(ItemTooltipEvent e) {
        e.getToolTip().add(new TranslationTextComponent("tooltip.detailedinformation." + e.getItemStack().getItem().getTranslationKey())
                .setStyle(new Style().setColor(TextFormatting.GRAY)));
    }

}
