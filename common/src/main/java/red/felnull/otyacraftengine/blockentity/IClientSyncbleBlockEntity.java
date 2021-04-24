package red.felnull.otyacraftengine.blockentity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;

public interface IClientSyncbleBlockEntity {

    CompoundTag clientSyncbleData(ServerPlayer player, CompoundTag tag);

    void clientSyncble(CompoundTag tag);

    void syncble();
}
