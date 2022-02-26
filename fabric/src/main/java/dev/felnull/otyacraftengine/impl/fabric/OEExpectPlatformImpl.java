package dev.felnull.otyacraftengine.impl.fabric;

import dev.felnull.otyacraftengine.fabric.mixin.MobBucketItemAccessor;
import dev.felnull.otyacraftengine.fabric.tag.TagCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class OEExpectPlatformImpl {
    public static Set<ResourceLocation> getItemTags(Item item) {
        return TagCache.ITEM.getTags(item);
    }

    public static Set<ResourceLocation> getBlockTags(Block item) {
        return TagCache.BLOCK.getTags(item);
    }

    public static Set<ResourceLocation> getEntityTypeTags(EntityType<?> entityType) {
        return TagCache.ENTITY_TYPE.getTags(entityType);
    }

    public static EntityType<?> getMobBucketEntity(MobBucketItem mobBucketItem) {
        return ((MobBucketItemAccessor) mobBucketItem).getType();
    }
}
