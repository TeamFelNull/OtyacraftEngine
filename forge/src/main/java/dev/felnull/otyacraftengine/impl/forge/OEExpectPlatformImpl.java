package dev.felnull.otyacraftengine.impl.forge;

import dev.felnull.otyacraftengine.forge.mixin.MobBucketItemAccessor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class OEExpectPlatformImpl {
    public static Set<ResourceLocation> getItemTags(Item item) {
        return item.getTags();
    }

    public static Set<ResourceLocation> getBlockTags(Block block) {
        return block.getTags();
    }

    public static Set<ResourceLocation> getEntityTypeTags(EntityType<?> entityType) {
        return entityType.getTags();
    }

    public static EntityType<?> getMobBucketEntity(MobBucketItem mobBucketItem) {
        return ((MobBucketItemAccessor) mobBucketItem).getFishTypeInvoker();
    }
}
