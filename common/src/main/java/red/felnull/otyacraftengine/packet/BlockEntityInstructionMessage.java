package red.felnull.otyacraftengine.packet;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class BlockEntityInstructionMessage implements IPacketMessage {
    public ResourceLocation dimension;
    public BlockPos pos;
    public ResourceLocation beName;
    public String name;
    public CompoundTag data;

    public BlockEntityInstructionMessage() {

    }

    public BlockEntityInstructionMessage(ResourceLocation dimension, BlockPos pos, ResourceLocation beName, String name, CompoundTag data) {
        this.dimension = dimension;
        this.pos = pos;
        this.beName = beName;
        this.name = name;
        this.data = data;
    }

    @Override
    public void decode(FriendlyByteBuf buffer) {
        this.dimension = buffer.readResourceLocation();
        this.pos = buffer.readBlockPos();
        this.beName = buffer.readResourceLocation();
        this.name = buffer.readUtf(32767);
        this.data = buffer.readNbt();
    }

    @Override
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(dimension);
        buffer.writeBlockPos(pos);
        buffer.writeResourceLocation(beName);
        buffer.writeUtf(name);
        buffer.writeNbt(data);
    }
}
