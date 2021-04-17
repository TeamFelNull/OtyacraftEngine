package red.felnull.otyacraftengine.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;

import java.util.List;
import java.util.UUID;
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
}
