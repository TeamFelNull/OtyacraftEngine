package red.felnull.otyacraftengine.blockentity;

import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import red.felnull.otyacraftengine.packet.ClientTileEntitySyncMessage;
import red.felnull.otyacraftengine.util.IKSGPacketUtil;

public interface IClientSyncbleBlockEntity {

    default public CompoundTag clientSyncbleData(CompoundTag tag) {
        if (this instanceof BlockEntity) {
            BlockEntity be = (BlockEntity) this;
            return be.save(tag);
        }
        return tag;
    }

    default public void clientSyncble(CompoundTag tag) {
        if (this instanceof BlockEntity) {
            BlockEntity be = (BlockEntity) this;
            be.load(tag);
        }
    }

    default public void syncble() {
        if (this instanceof BlockEntity) {
            BlockEntity be = (BlockEntity) this;
            Level level = be.getLevel();
            if (level.isClientSide())
                return;

            LevelChunk lch = (LevelChunk) be.getLevel().getChunk(be.getBlockPos());
            ResourceLocation dimension = level.dimension().location();
            ResourceLocation bereg = Registry.BLOCK_ENTITY_TYPE.getKey(be.getType());
            IKSGPacketUtil.sendToClientPacket(lch, new ClientTileEntitySyncMessage(dimension, be.getBlockPos(), bereg, clientSyncbleData(new CompoundTag())));

        }
    }
}
