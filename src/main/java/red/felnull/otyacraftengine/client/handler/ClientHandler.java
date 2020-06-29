package red.felnull.otyacraftengine.client.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.InputMappings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import red.felnull.otyacraftengine.client.config.ClientConfig;
import red.felnull.otyacraftengine.item.IDetailedInfomationItem;
import red.felnull.otyacraftengine.util.ModUtil;
import red.felnull.otyacraftengine.util.TagHelper;

import java.util.Objects;

public class ClientHandler {
    private static Minecraft mc = Minecraft.getInstance();

    private static void addDetailedInformation(ItemTooltipEvent e) {
        ItemStack stack = e.getItemStack();
        if (stack.getItem() instanceof IDetailedInfomationItem) {

            if (!InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), mc.gameSettings.keyBindSneak.getKeyBinding().getKey().getKeyCode())) {
                e.getToolTip().add(new TranslationTextComponent("tooltip.detailedinformation.press", mc.gameSettings.keyBindSneak.func_238171_j_()).func_240699_a_(TextFormatting.WHITE));
                return;
            }
            e.getToolTip().add(new TranslationTextComponent("tooltip.detailedinformation").func_240699_a_(TextFormatting.YELLOW));
            ((IDetailedInfomationItem) stack.getItem()).addDetailedInformation(e);
        }

    }

    private static void addTagList(ItemTooltipEvent e) {

        ItemStack stack = e.getItemStack();

        boolean itemtagflag = !TagHelper.getItemTags(stack).isEmpty();
        boolean blocktagflag = (stack.getItem() instanceof BlockItem && !TagHelper.getBlockTags(stack).isEmpty());
        boolean entitytagflag = (stack.getItem() instanceof SpawnEggItem && !Objects.requireNonNull(TagHelper.getEntityTags(stack)).isEmpty());

        if (!(itemtagflag || blocktagflag || entitytagflag))
            return;

        if (!InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), mc.gameSettings.keyBindSprint.getKeyBinding().getKey().getKeyCode())) {
            e.getToolTip().add(new TranslationTextComponent("tooltip.tag.press", mc.gameSettings.keyBindSprint.func_238171_j_()).func_240699_a_(TextFormatting.WHITE));
            return;
        }

        if (itemtagflag) {

            e.getToolTip().add(new TranslationTextComponent("tooltip.tag.item").func_240699_a_(TextFormatting.AQUA));
            TagHelper.getItemTags(stack).forEach(tags -> e.getToolTip().add(new StringTextComponent(tags.toString()).func_240699_a_(TextFormatting.GRAY)));
        }
        if (blocktagflag) {
            e.getToolTip().add(new TranslationTextComponent("tooltip.tag.block").func_240699_a_(TextFormatting.AQUA));
            TagHelper.getBlockTags(stack).forEach(tags -> e.getToolTip().add(new StringTextComponent(tags.toString()).func_240699_a_(TextFormatting.GRAY)));
        }
        if (entitytagflag) {
            e.getToolTip().add(new TranslationTextComponent("tooltip.tag.entitytype").func_240699_a_(TextFormatting.AQUA));
            Objects.requireNonNull(TagHelper.getEntityTags(stack)).forEach(tags -> e.getToolTip().add(new StringTextComponent(tags.toString()).func_240699_a_(TextFormatting.GRAY)));
        }

    }

    private static void addModName(ItemTooltipEvent e) {
        e.getToolTip().add(new StringTextComponent(ModUtil.getModName(ModUtil.getModID(e.getItemStack())) + " " + ModUtil.getModVersion(ModUtil.getModID(e.getItemStack()))).func_240699_a_(ModUtil.getModColor(ModUtil.getModID(e.getItemStack()))));
    }

    @SubscribeEvent
    public void onToolTip(ItemTooltipEvent e) {

        if (ClientConfig.ToolTipDetailedInformation.get())
            addDetailedInformation(e);

        if (ClientConfig.ToolTipTag.get())
            addTagList(e);

        if (ClientConfig.ToolTipModName.get())
            addModName(e);
    }


}
