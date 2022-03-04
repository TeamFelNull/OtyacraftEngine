package dev.felnull.otyacraftengine.impl;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.MobBucketItem;

import java.util.List;

public class OEExpectPlatform {
    @ExpectPlatform
    public static EntityType<?> getMobBucketEntity(MobBucketItem mobBucketItem) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T> List<T> getEntryPoints(String name, Class<?> annotationClass, Class<T> interfaceClass) {
        throw new AssertionError();
    }
}
