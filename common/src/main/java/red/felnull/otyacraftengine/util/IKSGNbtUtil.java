package red.felnull.otyacraftengine.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;

import java.util.*;
import java.util.function.Function;

public class IKSGNbtUtil {

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
        return writeList(tag, name, new ArrayList<>(map.entrySet()), n -> {
            CompoundTag it = new CompoundTag();
            it.put("k", writerKey.apply(n.getKey()));
            it.put("e", writerEntry.apply(n.getValue()));
            return it;
        });
    }

    public static <T, M> void readMap(CompoundTag tag, String name, Map<T, M> map, Function<Tag, T> readerKey, Function<Tag, M> readerEntry, int num) {
        List<Map.Entry<T, M>> entries = new ArrayList<>();
        readList(tag, name, entries, n -> {
            CompoundTag it = new CompoundTag();
            return new AbstractMap.SimpleEntry<>(readerKey.apply(it.get("k")), readerEntry.apply(it.get("e")));
        }, num);
        map.clear();
        entries.forEach(n -> map.put(n.getKey(), n.getValue()));
    }
}
