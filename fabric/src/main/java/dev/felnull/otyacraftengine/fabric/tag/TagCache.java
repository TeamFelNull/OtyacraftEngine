package dev.felnull.otyacraftengine.fabric.tag;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.*;
import java.util.function.Supplier;

public class TagCache<T> {
    public static final TagCache<Item> ITEM = new TagCache<>(ItemTags::getAllTags);
    public static final TagCache<Block> BLOCK = new TagCache<>(BlockTags::getAllTags);
    public static final TagCache<EntityType<?>> ENTITY_TYPE = new TagCache<>(EntityTypeTags::getAllTags);

    private final Supplier<TagCollection<T>> tagSupplier;
    private final Map<T, Cache> chashs = new HashMap<>();

    public TagCache(Supplier<TagCollection<T>> tagSupplier) {
        this.tagSupplier = tagSupplier;
    }

    public Set<ResourceLocation> getTags(T target) {
        if (!chashs.containsKey(target))
            chashs.put(target, new Cache());

        var tags = tagSupplier.get();
        var ch = chashs.get(target);
        if (ch.cache == null || ch.allCache != tags.getAllTags()) {
            ch.cache = Collections.unmodifiableSet(new HashSet<>(tags.getMatchingTags(target)));
            ch.allCache = tags.getAllTags();
        }
        return ch.cache;
    }

    private class Cache {
        private Map<ResourceLocation, Tag<T>> allCache;
        private Set<ResourceLocation> cache;
    }
}
