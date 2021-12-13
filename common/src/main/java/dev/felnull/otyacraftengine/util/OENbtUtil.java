package dev.felnull.otyacraftengine.util;

import dev.felnull.otyacraftengine.data.ITAGSerializable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;

import java.util.*;
import java.util.function.Function;

public class OENbtUtil {
    public static CompoundTag writeUUIDList(CompoundTag tag, String name, List<UUID> uuids) {
        return writeList(tag, name, uuids, NbtUtils::createUUID);
    }

    public static <T> CompoundTag writeList(CompoundTag tag, String name, List<T> list, Function<T, Tag> writer) {
        ListTag listTag = new ListTag();
        list.forEach(n -> listTag.add(writer.apply(n)));
        tag.put(name, listTag);
        return tag;
    }

    public static void readUUIDList(CompoundTag tag, String name, List<UUID> uuids) {
        readList(tag, name, uuids, NbtUtils::loadUUID, 11);
    }

    public static <T> void readList(CompoundTag tag, String name, List<T> list, Function<Tag, T> reader, int num) {
        list.clear();
        ListTag listTag = tag.getList(name, num);
        for (Tag lstag : listTag) {
            list.add(reader.apply(lstag));
        }
    }

    public static <T> void readList(CompoundTag tag, String name, List<T> list, Function<Tag, T> reader) {
        readList(tag, name, list, reader, 10);
    }

    public static <T, M> CompoundTag writeMap(CompoundTag tag, String name, Map<T, M> map, Function<T, Tag> writerKey, Function<M, Tag> writerEntry) {

        Function<Map.Entry<T, M>, Tag> writer = n -> {
            CompoundTag it = new CompoundTag();
            it.put("K", writerKey.apply(n.getKey()));
            it.put("E", writerEntry.apply(n.getValue()));
            return it;
        };
        return writeList(tag, name, new ArrayList<>(map.entrySet()), writer);
    }

    public static <T, M> void readMap(CompoundTag tag, String name, Map<T, M> map, Function<Tag, T> readerKey, Function<Tag, M> readerEntry) {
        List<Map.Entry<T, M>> entries = new ArrayList<>();
        Function<Tag, Map.Entry<T, M>> reader = n -> {
            CompoundTag it = (CompoundTag) n;
            return new AbstractMap.SimpleEntry<>(readerKey.apply(it.get("K")), readerEntry.apply(it.get("E")));
        };
        readList(tag, name, entries, reader, 10);
        map.clear();
        entries.forEach(n -> map.put(n.getKey(), n.getValue()));
    }

    public static <T extends ITAGSerializable> CompoundTag writeSerializable(CompoundTag tag, String name, T serializable) {
        tag.put(name, serializable.save(new CompoundTag()));
        return tag;
    }

    public static <T extends ITAGSerializable> T readSerializable(CompoundTag tag, String name, T serializable) {
        serializable.load(tag.getCompound(name));
        return serializable;
    }

    public static CompoundTag writeMap(CompoundTag tag, String name, Map<UUID, CompoundTag> map) {
        return writeMap(tag, name, map, NbtUtils::createUUID, n -> n);
    }

    public static void readMap(CompoundTag tag, String name, Map<UUID, CompoundTag> map) {
        readMap(tag, name, map, NbtUtils::loadUUID, n -> (CompoundTag) n);
    }
}
