package red.felnull.otyacraftengine.handler;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fml.network.NetworkEvent;
import red.felnull.otyacraftengine.packet.PacketHandler;
import red.felnull.otyacraftengine.packet.TileEntityInstructionMessage;
import red.felnull.otyacraftengine.packet.TileEntityInstructionReturnMessage;
import red.felnull.otyacraftengine.tileentity.IInstructionTileEntity;

import java.util.function.Supplier;

public class TileEntityInstructionMessageHandler {
    public static void reversiveMessage(TileEntityInstructionMessage message, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getSender().world.getTileEntity(message.pos) instanceof IInstructionTileEntity) {
            IInstructionTileEntity ifc = (IInstructionTileEntity) ctx.get().getSender().world.getTileEntity(message.pos);
            if (ifc.canInteractWith(ctx.get().getSender(), message.name, message.data)) {
                CompoundNBT retag = ifc.instructionFromClient(ctx.get().getSender(), message.name, message.data);
                if (retag != null) {
                    PacketHandler.sendPacket(ctx.get().getSender(), new TileEntityInstructionReturnMessage(message.pos, message.name, retag));
                }
            }
        }
    }
}
