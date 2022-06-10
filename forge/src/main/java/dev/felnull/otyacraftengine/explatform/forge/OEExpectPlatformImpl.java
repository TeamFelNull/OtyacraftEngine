package dev.felnull.otyacraftengine.explatform.forge;

import dev.felnull.otyacraftengine.forge.mixin.MobBucketItemAccessor;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;

import java.util.stream.Stream;

public class OEExpectPlatformImpl {
    public static EntityType<?> getMobBucketEntity(MobBucketItem mobBucketItem) {
        return ((MobBucketItemAccessor) mobBucketItem).getFishTypeInvoker();
    }

    public static Stream<TagKey<EntityType<?>>> getTags(EntityType<?> entityType) {
        return entityType.getTags();
    }

    public static String getItemCreatorModId(ItemStack stack) {
        return stack.getItem().getCreatorModId(stack);
    }
}
