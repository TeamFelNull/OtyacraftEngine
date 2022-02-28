package dev.felnull.otyacraftengine.impl;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.Set;

public class OEExpectPlatform {
    @ExpectPlatform
    public static Set<ResourceLocation> getItemTags(Item item) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Set<ResourceLocation> getBlockTags(Block block) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Set<ResourceLocation> getEntityTypeTags(EntityType<?> entityType) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static EntityType<?> getMobBucketEntity(MobBucketItem mobBucketItem) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T> List<T> getEntryPoints(String name, Class<?> annotationClass, Class<T> interfaceClass) {
        throw new AssertionError();
    }
}
