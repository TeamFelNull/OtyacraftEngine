package dev.felnull.otyacraftengine.explatform.fabric;

import dev.felnull.otyacraftengine.fabric.mixin.MobBucketItemAccessor;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;

import java.util.stream.Stream;

public class OEExpectPlatformImpl {
    public static EntityType<?> getMobBucketEntity(MobBucketItem mobBucketItem) {
        return ((MobBucketItemAccessor) mobBucketItem).getType();
    }

    public static Stream<TagKey<EntityType<?>>> getTags(EntityType<?> entityType) {
        return entityType.builtInRegistryHolder().tags();
    }

    public static String getItemCreatorModId(ItemStack stack) {
        return Registry.ITEM.getKey(stack.getItem()).getNamespace();
    }
}