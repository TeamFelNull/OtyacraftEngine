package dev.felnull.otyacraftengine.impl.fabric;

import dev.felnull.otyacraftengine.fabric.mixin.MobBucketItemAccessor;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.MobBucketItem;

import java.util.List;

public class OEExpectPlatformImpl {
    public static EntityType<?> getMobBucketEntity(MobBucketItem mobBucketItem) {
        return ((MobBucketItemAccessor) mobBucketItem).getType();
    }

    public static <T> List<T> getEntryPoints(String name, Class<?> annotationClass, Class<T> interfaceClass) {
        return FabricLoader.getInstance().getEntrypoints(name, interfaceClass);
    }
}
