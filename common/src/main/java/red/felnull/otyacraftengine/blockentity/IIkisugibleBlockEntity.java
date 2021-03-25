package red.felnull.otyacraftengine.blockentity;

import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import red.felnull.otyacraftengine.packet.ClientTileEntitySyncMessage;
import red.felnull.otyacraftengine.util.IKSGPacketUtil;

public interface IIkisugibleBlockEntity extends ITickbleBlockEntity, IClientSyncbleBlockEntity, IInstructionBlockEntity {

    BlockEntity getBlockEntity();

    default boolean isUsableByPlayer(Player player) {
        return getBlockEntity().getLevel().getBlockEntity(getBlockEntity().getBlockPos()) == this && player.distanceToSqr((double) getBlockEntity().getBlockPos().getX() + 0.5D, (double) getBlockEntity().getBlockPos().getY() + 0.5D, (double) getBlockEntity().getBlockPos().getZ() + 0.5D) <= 64.0D;
    }

    @Override
    default boolean tickble() {
        return false;
    }

    @Override
    default void tick() {
    }

    @Override
    default CompoundTag clientSyncbleData(CompoundTag tag) {
        return getBlockEntity().save(tag);
    }

    @Override
    default void clientSyncble(CompoundTag tag) {
        getBlockEntity().load(tag);
    }

    @Override
    default void syncble() {
        if (getBlockEntity().getLevel().isClientSide())
            return;

        LevelChunk lch = (LevelChunk) getBlockEntity().getLevel().getChunk(getBlockEntity().getBlockPos());
        ResourceLocation dimension = getBlockEntity().getLevel().dimension().location();
        ResourceLocation bereg = Registry.BLOCK_ENTITY_TYPE.getKey(getBlockEntity().getType());
        IKSGPacketUtil.sendToClientPacket(lch, new ClientTileEntitySyncMessage(dimension, getBlockEntity().getBlockPos(), bereg, clientSyncbleData(new CompoundTag())));
    }

    @Override
    default CompoundTag instructionFromClient(ServerPlayer player, String name, CompoundTag data) {
        return null;
    }

    @Override
    default boolean canInstructionWith(ServerPlayer player, String name, CompoundTag data) {
        return isUsableByPlayer(player);
    }

}
