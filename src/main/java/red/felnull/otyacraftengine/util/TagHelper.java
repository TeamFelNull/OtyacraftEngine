package red.felnull.otyacraftengine.util;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;

import java.util.Set;

public class TagHelper {
    public static Set<ResourceLocation> getItemTags(Item item) {
        return item.getTags();
    }

    @SuppressWarnings("deprecation")
    public static Set<ResourceLocation> getItemTags(Block block) {
        return getItemTags(Item.getItemFromBlock(block));
    }

    public static Set<ResourceLocation> getBlockTags(Block block) {
        return block.getTags();
    }

    public static Set<ResourceLocation> getBlockTags(Item item) {
        if (!(item instanceof BlockItem))
            return null;

        return getBlockTags(Block.getBlockFromItem(item));
    }

    public static Set<ResourceLocation> getEntityTags(EntityType<?> type) {
        return type.getTags();
    }

    public static Set<ResourceLocation> getEntityTags(Entity entity) {
        return entity.getType().getTags();
    }

    public static Set<ResourceLocation> getEntityTags(Item item) {
        return getEntityTags(new ItemStack(item));
    }

    public static Set<ResourceLocation> getEntityTags(ItemStack stack) {
        if (stack.getItem() instanceof SpawnEggItem) {
            return getEntityTags(((SpawnEggItem) stack.getItem()).getType(stack.getTag()));
        }
        return null;
    }

    public static Set<ResourceLocation> getItemTags(ItemStack stack) {
        return getItemTags(stack.getItem());
    }

    public static Set<ResourceLocation> getBlockTags(ItemStack stack) {
        return getBlockTags(stack.getItem());
    }
}
