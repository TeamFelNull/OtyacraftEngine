package dev.felnull.otyacraftengine.blockentity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface IClientSyncableBlockEntity {
    boolean isSyncUpdate();

    void saveToUpdateTag(CompoundTag tag);

    default void loadToUpdateTag(CompoundTag tag) {
        if (this instanceof BlockEntity blockEntity)
            blockEntity.load(tag);
    }

    default void syncToClient() {
        if (this instanceof BlockEntity blockEntity && isSyncUpdate())
            blockEntity.getLevel().sendBlockUpdated(blockEntity.getBlockPos(), blockEntity.getBlockState(), blockEntity.getBlockState(), Block.UPDATE_CLIENTS);
    }
}
