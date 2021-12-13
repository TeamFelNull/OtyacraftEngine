package dev.felnull.otyacraftengine.util;

import dev.felnull.otyacraftengine.impl.OEExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class OETagUtil {
    public static Set<ResourceLocation> getTags(Item item) {
        return OEExpectPlatform.getItemTags(item);
    }

    public static Set<ResourceLocation> getTags(Block block) {
        return OEExpectPlatform.getBlockTags(block);
    }

    public static Set<ResourceLocation> getTags(EntityType<?> entityType) {
        return OEExpectPlatform.getEntityTypeTags(entityType);
    }
}
