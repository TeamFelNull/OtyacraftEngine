package red.felnull.otyacraftengine.client.data;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import red.felnull.otyacraftengine.packet.PacketHandler;
import red.felnull.otyacraftengine.packet.TileEntityInstructionMessage;

public class InstructionFromClient {
    @OnlyIn(Dist.CLIENT)
    public static void instruction(BlockPos pos, String name, CompoundNBT data) {
        PacketHandler.INSTANCE.sendToServer(new TileEntityInstructionMessage(pos, name, data));
    }
}
