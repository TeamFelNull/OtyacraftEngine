package dev.felnull.otyacraftengine.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class OEBaseBlockEntity extends BlockEntity implements IClientSyncbleBlockEntity {
    public OEBaseBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    @Override
    public CompoundTag getSyncData(ServerPlayer player, CompoundTag tag) {
        return null;
    }

    @Override
    public void onSync(CompoundTag tag) {
    }

    @Override
    public void sync() {
        IClientSyncbleBlockEntity.syncBlockEntity(this);
    }
}
