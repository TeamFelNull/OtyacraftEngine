package red.felnull.otyacraftengine.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import red.felnull.otyacraftengine.packet.ClientTileEntitySyncMessage;
import red.felnull.otyacraftengine.util.IKSGPacketUtil;

public class IkisugiBlockEntity extends BlockEntity implements IClientSyncbleBlockEntity {
    public IkisugiBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    @Override
    public CompoundTag clientSyncbleData(CompoundTag tag) {
        return this.save(tag);
    }

    @Override
    public void clientSyncble(CompoundTag tag) {
        this.load(tag);
    }

    @Override
    public void syncble() {
        if (this.getLevel().isClientSide())
            return;

        LevelChunk lch = (LevelChunk) this.getLevel().getChunk(this.getBlockPos());
        ResourceLocation dimension = this.getLevel().dimension().location();
        ResourceLocation bereg = Registry.BLOCK_ENTITY_TYPE.getKey(this.getType());
        IKSGPacketUtil.sendToClientPacket(lch, new ClientTileEntitySyncMessage(dimension, this.getBlockPos(), bereg, clientSyncbleData(new CompoundTag())));
    }
}
