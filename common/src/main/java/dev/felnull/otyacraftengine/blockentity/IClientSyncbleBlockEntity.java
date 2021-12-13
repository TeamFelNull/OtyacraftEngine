package dev.felnull.otyacraftengine.blockentity;

import dev.architectury.networking.NetworkManager;
import dev.felnull.otyacraftengine.networking.OEPackets;
import dev.felnull.otyacraftengine.util.OEPlayerUtil;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;

public interface IClientSyncbleBlockEntity {
    public static void syncBlockEntity(BlockEntity blockEntity) {
        if (blockEntity instanceof IClientSyncbleBlockEntity syncble) {
            LevelChunk lch = (LevelChunk) blockEntity.getLevel().getChunk(blockEntity.getBlockPos());
            OEPlayerUtil.doPlayers(lch, player -> {
                CompoundTag tag = syncble.getSyncData(player, new CompoundTag());
                if (tag != null) {
                    ResourceLocation bereg = Registry.BLOCK_ENTITY_TYPE.getKey(blockEntity.getType());
                    NetworkManager.sendToPlayer(player, OEPackets.BLOCK_ENTITY_SYNC, new OEPackets.BlockEntitySyncMessage(blockEntity.getLevel().dimension().location(), blockEntity.getBlockPos(), bereg, tag).toFBB());
                }
            });
        }
    }

    CompoundTag getSyncData(ServerPlayer player, CompoundTag tag);

    void onSync(CompoundTag tag);

    void sync();

}
