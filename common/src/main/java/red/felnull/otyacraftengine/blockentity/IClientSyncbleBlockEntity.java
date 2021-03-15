package red.felnull.otyacraftengine.blockentity;

import net.minecraft.nbt.CompoundTag;

public interface IClientSyncbleBlockEntity {

    CompoundTag clientSyncbleData(CompoundTag tag);

    void clientSyncble(CompoundTag tag);

    void syncble();
}
