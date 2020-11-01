package red.felnull.otyacraftengine.util;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import red.felnull.otyacraftengine.client.config.ClientConfig;
import red.felnull.otyacraftengine.client.util.IKSGClientUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IKSGTagUtil {
    public static Set<ResourceLocation> getItemTags(Item item) {
        return item.getTags();
    }

    public static Set<ResourceLocation> getItemTags(Block block) {
        return getItemTags(Item.getItemFromBlock(block));
    }

    public static Set<ResourceLocation> getBlockTags(Block block) {
        return block.getTags();
    }

    public static Set<ResourceLocation> getBlockTags(Item item) {
        if (!(item instanceof BlockItem))
            return new HashSet<ResourceLocation>();
        return getBlockTags(Block.getBlockFromItem(item));
    }

    public static Set<ResourceLocation> getEntityTags(EntityType<?> type) {
        return type.getTags();
    }

    public static Set<ResourceLocation> getEntityTagsByItem(ItemStack stack) {
        if (stack.getItem() instanceof SpawnEggItem) {
            return getEntityTags(((SpawnEggItem) stack.getItem()).getType(stack.getTag()));
        }
        return new HashSet<ResourceLocation>();
    }

    public static Set<ResourceLocation> getEnchantmentTags(Enchantment enchantment) {
        return enchantment.getTags();
    }

    public static Set<ResourceLocation> getPotionTags(Potion potion) {
        return potion.getTags();
    }

    public static Set<ResourceLocation> getTileEntityTags(TileEntityType<?> tile) {
        return tile.getTags();
    }

    @OnlyIn(Dist.CLIENT)
    public static void addTagTooltip(ItemStack itemIn, List<ITextComponent> lores) {

        Set<ResourceLocation> itemTags = getItemTags(itemIn.getItem());
        Set<ResourceLocation> blockTags = getBlockTags(itemIn.getItem());
        Set<ResourceLocation> entityTags = getEntityTagsByItem(itemIn);
        Set<ResourceLocation> potionTags = getPotionTags(PotionUtils.getPotionFromItem(itemIn));

        if (itemTags.isEmpty() && blockTags.isEmpty() && entityTags.isEmpty() && potionTags.isEmpty())
            return;

        if (!IKSGClientUtil.isKeyInput(ClientConfig.ToolTipTagKey.get(), true)) {
            lores.add(new TranslationTextComponent("tooltip.tag.press", IKSGClientUtil.getKeyBind(ClientConfig.ToolTipTagKey.get()).func_238171_j_()).mergeStyle(TextFormatting.WHITE));
            return;
        }

        if (!itemTags.isEmpty()) {
            lores.add(new TranslationTextComponent("tooltip.tag.item").mergeStyle(TextFormatting.AQUA));
            itemTags.forEach(tags -> lores.add(new StringTextComponent("- " + tags.toString()).mergeStyle(TextFormatting.GRAY)));
        }
        if (!blockTags.isEmpty()) {
            lores.add(new TranslationTextComponent("tooltip.tag.block").mergeStyle(TextFormatting.AQUA));
            blockTags.forEach(tags -> lores.add(new StringTextComponent("- " + tags.toString()).mergeStyle(TextFormatting.GRAY)));
        }
        if (!entityTags.isEmpty()) {
            lores.add(new TranslationTextComponent("tooltip.tag.entitytype").mergeStyle(TextFormatting.AQUA));
            entityTags.forEach(tags -> lores.add(new StringTextComponent("- " + tags.toString()).mergeStyle(TextFormatting.GRAY)));
        }
        if (!potionTags.isEmpty()) {
            lores.add(new TranslationTextComponent("tooltip.tag.potion").mergeStyle(TextFormatting.AQUA));
            potionTags.forEach(tags -> lores.add(new StringTextComponent("- " + tags.toString()).mergeStyle(TextFormatting.GRAY)));
        }
    }

}
