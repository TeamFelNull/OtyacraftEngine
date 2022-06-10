package dev.felnull.otyacraftengine.explatform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;

import java.util.stream.Stream;

public class OEExpectPlatform {
    @ExpectPlatform
    public static EntityType<?> getMobBucketEntity(MobBucketItem mobBucketItem) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Stream<TagKey<EntityType<?>>> getTags(EntityType<?> entityType) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static String getItemCreatorModId(ItemStack item) {
        throw new AssertionError();
    }
}
