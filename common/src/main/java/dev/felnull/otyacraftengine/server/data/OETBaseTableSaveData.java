package dev.felnull.otyacraftengine.server.data;

import dev.felnull.otyacraftengine.util.OENbtUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

public abstract class OETBaseTableSaveData<E extends ITAGSerializable> extends OEBaseSaveData {
    private final Map<UUID, E> entry = new HashMap<>();
    private final Supplier<E> entryCreate;

    private OETBaseTableSaveData(Supplier<E> entryCreate) {
        super();
        this.entryCreate = entryCreate;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        OENbtUtil.writeMap(tag, "Entrys", entry, NbtUtils::createUUID, n -> OENbtUtil.writeSerializable(new CompoundTag(), "e", n));
        return tag;
    }

    @Override
    public void load(CompoundTag tag) {
        OENbtUtil.readMap(tag, "Entrys", entry, NbtUtils::loadUUID, n -> OENbtUtil.readSerializable(tag, "e", entryCreate.get()));
    }

    @Override
    public void clear() {

    }
}
