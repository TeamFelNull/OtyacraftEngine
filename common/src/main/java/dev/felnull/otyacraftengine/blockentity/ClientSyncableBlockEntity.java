package dev.felnull.otyacraftengine.blockentity;

import dev.architectury.networking.NetworkManager;
import dev.felnull.otyacraftengine.networking.OEPackets;
import dev.felnull.otyacraftengine.networking.existence.BlockEntityExistence;
import dev.felnull.otyacraftengine.util.OEPlayerUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public interface ClientSyncableBlockEntity {

    default void saveSyncData(@NotNull ServerPlayer player, @NotNull CompoundTag tag) {
        saveSyncData(tag);
    }

    void saveSyncData(@NotNull CompoundTag tag);

    void onSync(@NotNull CompoundTag tag);

    default void sync() {
        if (!isSync()) return;
        if (this instanceof BlockEntity blockEntity) {
            OEPlayerUtils.doPlayers(Objects.requireNonNull(blockEntity.getLevel()), blockEntity.getBlockPos(), player -> {
                var tag = new CompoundTag();
                saveSyncData(player, tag);
                NetworkManager.sendToPlayer(player, OEPackets.BLOCK_ENTITY_SYNC, new OEPackets.BlockEntitySyncMessage(BlockEntityExistence.getByBlockEntity(blockEntity), tag).toFBB());
            });
        }
    }

    boolean isSync();
}
