package red.felnull.otyacraftengine.packet;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;

public class TileEntityInstructionReturnMessage {
    public BlockPos pos;
    public String name;
    public CompoundNBT data;

    public TileEntityInstructionReturnMessage(BlockPos pos, String name, CompoundNBT data) {
        this.pos = pos;
        this.name = name;
        this.data = data;
    }

    public static TileEntityInstructionReturnMessage decodeMessege(PacketBuffer buffer) {
        return new TileEntityInstructionReturnMessage(buffer.readBlockPos(), buffer.readString(32767), buffer.readCompoundTag());
    }

    public static void encodeMessege(TileEntityInstructionReturnMessage messegeIn, PacketBuffer buffer) {
        buffer.writeBlockPos(messegeIn.pos);
        buffer.writeString(messegeIn.name);
        buffer.writeCompoundTag(messegeIn.data);
    }
}
